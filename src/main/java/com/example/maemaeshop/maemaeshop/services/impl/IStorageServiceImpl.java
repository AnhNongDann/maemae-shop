package com.example.maemaeshop.maemaeshop.services.impl;

import com.example.maemaeshop.maemaeshop.services.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class IStorageServiceImpl implements IStorageService {

    private final Path storageFolder= Paths.get("uploads");

    public IStorageServiceImpl()
    {
        try {
            Files.createDirectories(storageFolder);
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Cannot init storage", exception);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        try{
            System.out.println("haha");
            if(file.isEmpty())
            {
                throw new RuntimeException("Failed to upload empty file");
            }
            if(!isFileName(file))
            {
                throw new RuntimeException("you can only upload image file");
            }

            float fileSize= file.getSize()/1_000_000.0f;
            if (fileSize>0.5f)
            {
                throw new RuntimeException("file must be <=5mb");
            }

            String fileExtension= FilenameUtils.getExtension(file.getOriginalFilename());
            String generationFileName= UUID.randomUUID()+"."+fileExtension;
            Path destinationFilePath= this.storageFolder.resolve(Paths.get(generationFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath()))
            {
                throw new RuntimeException("cannot store file outside current directory");
            }
            try(InputStream inputStream= file.getInputStream()) {
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            }

            return generationFileName;

        }

        catch (IOException exception){
            throw new RuntimeException("Fail to store file", exception);
        }
    }

    private boolean isFileName(MultipartFile file)
    {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(storageFolder,1).filter(
                    path -> !path.equals(this.storageFolder)
            ).map(this.storageFolder::relativize);
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Fail to load stored files", exception);
        }
    }

    @Override
    public byte[] readFileContent(String filename) {
        try
        {
            Path file = storageFolder.resolve(filename);
            Resource resource= new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable())
            {
                byte[] bytes= StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException("could not read file"+filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
