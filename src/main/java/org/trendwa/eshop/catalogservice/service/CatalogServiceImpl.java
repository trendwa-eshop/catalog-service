package org.trendwa.eshop.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;
import org.trendwa.eshop.catalogservice.mapper.CatalogBrandMapper;
import org.trendwa.eshop.catalogservice.mapper.CatalogItemMapper;
import org.trendwa.eshop.catalogservice.mapper.CatalogTypeMapper;
import org.trendwa.eshop.catalogservice.model.CatalogBrand;
import org.trendwa.eshop.catalogservice.model.CatalogItem;
import org.trendwa.eshop.catalogservice.model.CatalogType;
import org.trendwa.eshop.catalogservice.repository.CatalogBrandRepository;
import org.trendwa.eshop.catalogservice.repository.CatalogItemRepository;
import org.trendwa.eshop.catalogservice.repository.CatalogTypeRepository;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    private final CatalogItemRepository catalogItemRepository;
    private final CatalogBrandRepository catalogBrandRepository;
    private final CatalogTypeRepository catalogTypeRepository;
    private final FileStorageService fileStorageService;

    @Override
    public List<CatalogBrandDto> getBrands(Pageable pageable) {
        Page<CatalogBrand> page = catalogBrandRepository.findAll(pageable);
        return page.map(CatalogBrandMapper::mapToDto).getContent();
    }

    @Override
    public List<CatalogTypeDto> getTypes(Pageable pageable) {
        Page<CatalogType> page = catalogTypeRepository.findAll(pageable);
        return page.map(CatalogTypeMapper::mapToDto).getContent();
    }


    @Override
    public List<CatalogItemDto> getAll(Pageable pageable) {

        Page<CatalogItem> page = catalogItemRepository.findAll(pageable);
        return page.map(CatalogItemMapper::mapToDto).getContent();
    }

    @Override
    public CatalogItemDto getItemById(Long id) {
        CatalogItem catalogItem = catalogItemRepository.findById(id).orElseThrow(() -> new CatalogItemNotFoundException("Catalog item not found with id: " + id));
        return CatalogItemMapper.mapToDto(catalogItem);
    }

    @Override
    public List<CatalogItemDto> getItemsByIds(List<Long> ids) {
        var items = catalogItemRepository.findAllById(ids);
        return items.stream().map(CatalogItemMapper::mapToDto).toList();
    }

    @Override
    public List<CatalogItemDto> getItemsByNameContaining(String name, Pageable pageable) {
        List<CatalogItem> items = catalogItemRepository.findByNameContaining(name, pageable);
        return items.stream().map(CatalogItemMapper::mapToDto).toList();
    }

    @Override
    public List<CatalogItemDto> getItemsByBrand(String brand, Pageable pageable) {
        List<CatalogItem> items = catalogItemRepository.findByCatalogBrandName(brand, pageable);
        return items.stream().map(CatalogItemMapper::mapToDto).toList();
    }

    @Override
    public List<CatalogItemDto> getItemsByType(String type, Pageable pageable) {
        List<CatalogItem> items = catalogItemRepository.findByCatalogTypeName(type, pageable);
        return items.stream().map(CatalogItemMapper::mapToDto).toList();
    }

    @Override
    public List<CatalogItemDto> getItemsByBrandAndType(String brand, String type, Pageable pageable) {
        List<CatalogItem> items = catalogItemRepository.findByCatalogBrandNameAndCatalogTypeName(brand, type, pageable);
        return items.stream().map(CatalogItemMapper::mapToDto).toList();
    }

    @Override
    @Transactional
    public CatalogItemDto create(CatalogItemDto item, MultipartFile productImage) throws DataIntegrityViolationException, IOException {

        if(item.getId() != null) {
            throw new IllegalArgumentException("Id must be null for new entity");
        }
        if (isFileValid(productImage)) {
            uploadNewFile(item, productImage);
        }
        CatalogItem itemToSave = CatalogItemMapper.mapToEntity(item);
        CatalogItem savedItem = catalogItemRepository.save(itemToSave);
        catalogItemRepository.refresh(savedItem);
        return CatalogItemMapper.mapToDto(savedItem);
    }

    @Override
    @Transactional
    public void update(CatalogItemDto item, MultipartFile productImage) throws IOException {
        CatalogItem existingItem = catalogItemRepository.findById(item.getId()).orElseThrow(() -> new CatalogItemNotFoundException(item.getId()));
        handleFileUpload(item, productImage, existingItem);
        catalogItemRepository.save(CatalogItemMapper.mapToEntity(item));
    }

    @Override
    public void deleteById(Long id) {
        if (catalogItemRepository.existsById(id)) {
            catalogItemRepository.deleteById(id);
            return;
        }
        throw new CatalogItemNotFoundException(id);
    }

    @Override
    public void flush() {
        catalogItemRepository.flush();
    }

    public void handleFileUpload(CatalogItemDto item, MultipartFile file, CatalogItem existingItem) throws IOException {
        if (isFileValid(file)) {
            if (isNewFile(existingItem, file)) {
                uploadNewFile(item, file);
            } else {
                reuseExistingFile(item, existingItem);
            }
        }
    }

    private boolean isFileValid(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    private boolean isNewFile(CatalogItem existingItem, MultipartFile file) {
        return existingItem == null || (StringUtils.isNotEmpty(file.getOriginalFilename()) && !file.getOriginalFilename().equals(existingItem.getPictureFileName()));
    }

    private void uploadNewFile(CatalogItemDto item, MultipartFile file) throws IOException {
        String uploadedFileUrl = fileStorageService.upload(file);
        item.setPictureFileName(file.getOriginalFilename());
        item.setPictureUri(uploadedFileUrl);
    }

    private void reuseExistingFile(CatalogItemDto item, CatalogItem existingItem) {
        item.setPictureFileName(existingItem.getPictureFileName());
        item.setPictureUri(existingItem.getPictureUri());
    }
}
