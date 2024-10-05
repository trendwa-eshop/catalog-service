package org.trendwa.eshop.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;
import org.trendwa.eshop.catalogservice.mapper.CatalogItemMapper;
import org.trendwa.eshop.catalogservice.model.CatalogItem;
import org.trendwa.eshop.catalogservice.repository.CatalogItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceImpl implements CatalogService{

    private final CatalogItemRepository catalogItemRepository;

    @Override
    public List<CatalogItemDto> getAll(Pageable pageable) {

        log.debug("getAll called with pageable: {}", pageable);
        Page<CatalogItem> page = catalogItemRepository.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())
        );
        List<CatalogItemDto> items = page.map(CatalogItemMapper::mapToDto).getContent();
        log.debug("getAll completed with items: {}", items);
        return items;
    }

    @Override
    public CatalogItemDto getItemById(Long id) {
        log.debug("getItemById called with id: {}", id);
        CatalogItem catalogItem = catalogItemRepository.findById(id).orElseThrow(() -> new CatalogItemNotFoundException("Catalog item not found with id: " + id));
        CatalogItemDto itemDto = CatalogItemMapper.mapToDto(catalogItem);
        log.debug("getItemById completed with item: {}", itemDto);
        return itemDto;
    }

    @Override
    public List<CatalogItemDto> getItemsByIds(List<Long> ids) {
        log.debug("getItemsByIds called with ids: {}", ids);
        var items = catalogItemRepository.findAllById(ids);
        List<CatalogItemDto> itemDtos = items.stream().map(CatalogItemMapper::mapToDto).toList();
        log.debug("getItemsByIds completed with items: {}", itemDtos);
        return itemDtos;
    }

    @Override
    public List<CatalogItemDto> getItemsByNameContaining(String name, Pageable pageable) {
        log.debug("getItemsByNameContaining called with name: {} and pageable: {}", name, pageable);
        List<CatalogItem> items = catalogItemRepository.findByNameContaining(name, pageable);
        log.debug("getItemsByNameContaining completed with items: {}", items);
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
    public CatalogItemDto save(CatalogItemDto item) throws DataIntegrityViolationException {
        CatalogItem catalogItem = catalogItemRepository.save(CatalogItemMapper.mapToEntity(item));
        return CatalogItemMapper.mapToDto(catalogItem);
    }

    @Override
    public void deleteById(Long id) {
        if(catalogItemRepository.existsById(id)){
            catalogItemRepository.deleteById(id);
            return;
        }
        throw new CatalogItemNotFoundException("Catalog item not found with id: " + id);
    }

    @Override
    public void flush() {
        catalogItemRepository.flush();
    }

}
