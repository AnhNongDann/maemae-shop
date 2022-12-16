package com.example.maemaeshop.maemaeshop.controllers;

import com.example.maemaeshop.maemaeshop.dto.Product.ProductDto;
import com.example.maemaeshop.maemaeshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    ResponseEntity<List<ProductDto>> getAllProducts(){
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<ProductDto> insertProduct(@RequestBody ProductDto productDto)
    {
        return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable Long id)
    {
        return new ResponseEntity<>(productService.GetById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id)
    {
        try {
            return new ResponseEntity<>(productService.update(productDto, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteEmployee(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
