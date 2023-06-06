package com.uib.api.interfaces;

import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IFilesStorageService {

    public void save(MultipartFile file,  String uploadPath) throws FoundException;
    public List<String> getAllFiles(String filePath) throws NotFoundException;
}
