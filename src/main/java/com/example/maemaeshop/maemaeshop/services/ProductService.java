package com.example.maemaeshop.maemaeshop.services;

import com.example.maemaeshop.maemaeshop.dto.Product.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<ProductDto> getAll();

    public ProductDto GetById(Long id);

    public ProductDto save(ProductDto ProductDto);

    public void delete(Long id);

    public ProductDto update(ProductDto newProduct, Long id) throws Exception;

}
