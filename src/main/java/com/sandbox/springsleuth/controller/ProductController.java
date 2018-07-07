package com.sandbox.springsleuth.controller;

import com.sandbox.springsleuth.service.ProductService;
import com.sandbox.springsleuth.service.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping()
    public List<ProductDto> getProducts() {
        log.info("fetching all products...");
        List<ProductDto> products = productService.fetchAll();
        log.info("products have been fetched");
        return products;
    }


    @PostMapping
    public ProductDto saveProduct(@RequestBody ProductDto productDto) {
        log.info("saving product...");
        ProductDto dto = productService.save(productDto);
        return dto;
    }


    @GetMapping("external")
    public String callExternalEndpoint() {
        log.info("calling external endpoint...");
        String result = restTemplate.getForObject("http://localhost:8080/product", String.class);
        log.info("external endpoint have been called...");
        return "result";
    }

}
