package com.sandbox.springsleuth.service;

import com.sandbox.springsleuth.dal.entity.ProductEntity;
import com.sandbox.springsleuth.dal.repository.ProductRepository;
import com.sandbox.springsleuth.service.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {


    @Autowired
    private ProductRepository productRepository;




    @Transactional
    public ProductDto save(ProductDto productDto) {
        ProductEntity productEntity = convertToEntity(productDto);
        ProductEntity createdFileEntity = productRepository.saveAndFlush(productEntity);
        ProductDto dto = convertToDto(createdFileEntity);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> fetchAll() {
        log.info("fetching all products...");
        List<ProductEntity> entityList = productRepository.findAll();
        log.info("products have been fetched...");
        return entityList.stream()
                .map(ProductService::convertToDto)
                .collect(Collectors.toList());
    }


    public static ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto fileDto = new ProductDto();
        fileDto.setName(productEntity.getName());
        fileDto.setId(productEntity.getId());
        return fileDto;
    }

    public static ProductEntity convertToEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setId(productDto.getId());
        return productEntity;
    }


}
