package com.example.maemaeshop.maemaeshop.controllers;

import com.example.maemaeshop.maemaeshop.dto.Product.ProductDto;
import com.example.maemaeshop.maemaeshop.dto.Product.ResponseObject;
import com.example.maemaeshop.maemaeshop.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.List;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("api/FileUpload")
public class FileUploadController {
    @Autowired
    private IStorageService iStorageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file)
    {
        try{
            String generateFileName = iStorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","upload file successfully",generateFileName));
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok",exception.getMessage(),"")
            );
        }

    }

    @GetMapping("{fileName:.+}")
    //b007a290-d085-48c9-936c-df874dc470d9.jpg
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName)
    {
        try{
            byte[] bytes= iStorageService.readFileContent(fileName);
            return  ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }
        catch (Exception exception)
        {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("AllFiles")
    public ResponseEntity<ResponseObject> getUploadedFiles(){
        try {
            List<String> urls= iStorageService.loadAll().map(
                    path -> {String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"readDetailFile",path
                            .getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    }
            ).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok","List file succesfully",urls));
        }
        catch (Exception exception)
        {
            return ResponseEntity.ok(new ResponseObject("ok","List file fail",new String[]{}));
        }
    }
}
