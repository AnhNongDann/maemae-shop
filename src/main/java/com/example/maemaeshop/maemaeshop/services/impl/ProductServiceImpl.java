package com.example.maemaeshop.maemaeshop.services.impl;

import com.example.maemaeshop.maemaeshop.dto.Product.ProductDto;
import com.example.maemaeshop.maemaeshop.entities.Product;
import com.example.maemaeshop.maemaeshop.repositories.ProductRepository;
import com.example.maemaeshop.maemaeshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAll() {

        List<ProductDto> productDtoList= new ArrayList<>();
        List<Product> productList= repository.findAll();

        for(int i=0;i<productList.size();i++)
        {
            productDtoList.add(mapToDto(productList.get(i)));
        }

        return productDtoList;
    }

    @Override
    public ProductDto GetById(Long id) {
        Optional<Product> employeeContainer = repository.findById(id);
        if (employeeContainer.isPresent()) {
            Product newEmployee = employeeContainer.get();
            return mapToDto(newEmployee);
        }
        return null;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        return mapToDto(repository.save(mapToEntity(productDto)));
    }

    @Override
    public void delete(Long id) {
        Optional<Product> hasEmployee = repository.findById(id);
        if (hasEmployee.isPresent()) {
            repository.deleteById(id);
            System.out.print("Delete successfully");
        } else {
            System.out.print("Can't find empID to delete");
        }
    }

    @Override
    public ProductDto update(ProductDto productDto, Long id) throws Exception {
        Optional<Product> productContainer = repository.findById(id);

        if (productContainer.isPresent())
        {
            Product product = productContainer.get();

            product = mapToEntity(productDto);
            product.setId(id);
            return mapToDto(repository.save(product));
        }else {
            throw new Exception("Employee not found");
        }
    }

    private ProductDto mapToDto(Product product)
    {
        if (product!=null) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            return productDto;
        }
        return null;
    }

    private Product mapToEntity(ProductDto productDto)
    {
        if (productDto!=null) {
            Product product = modelMapper.map(productDto, Product.class);
            return product;
        }
        return null;
    }
}