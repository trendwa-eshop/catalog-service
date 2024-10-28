package org.trendwa.eshop.catalogservice.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@Schema(description = "Form for creating or updating a catalog item")
public class CatalogItemForm {

    @Schema(description = "JSON representation of the catalog item", type = "CatalogItemDto", implementation = CatalogItemDto.class)
    private String item;

    @Schema(description = "Image file for the catalog item")
    private MultipartFile image;

    public CatalogItemDto toDto() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(item, CatalogItemDto.class);
    }
}