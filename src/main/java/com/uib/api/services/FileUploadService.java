package com.uib.api.services;


import com.uib.api.dtos.FileUploadDTO;
import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.NotFoundException;
import com.uib.api.interfaces.IFilesStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileUploadService implements IFilesStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    private Path path = null;

    @Override
    public void save(MultipartFile file, String uploadPath) throws FoundException {
        path = Paths.get(uploadPath);
        logger.info("File Upload path :");
        FileUploadDTO fileUpload = new FileUploadDTO();
        fileUpload.setFileName(file.getOriginalFilename());
        fileUpload.setContentType(fileUpload.getContentType());
        fileUpload.setPath(path.toString());
        fileUpload.setContentType(file.getContentType());
        try {
            Files.copy(file.getInputStream(), this.path.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FoundException("A file of that name already exists.");
            }
            throw new FoundException(e.getMessage());
        }

    }

    @Override
    public List<String> getAllFiles(String filePath) throws NotFoundException {
        path = Paths.get(filePath);
        File dir = new File(path.toUri());
        String[] files = dir.list();
        if (files.length == 0) {
            throw new NotFoundException("Items not found");
        } else {
            return List.of(files);
        }

    }
}
