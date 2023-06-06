package com.uib.api.controllers;

import com.uib.api.dtos.FolderDTO;
import com.uib.api.dtos.ResponseMessageDTO;
import com.uib.api.enums.ResponseCode;
import com.uib.api.services.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/webservice/api/files")
public class FileUploaderController {

    private FileUploadService fileUploadService;

    public FileUploaderController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessageDTO> upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            fileUploadService.save(file, path);
            responseMessageDTO = new ResponseMessageDTO(null, "File has been uploaded!!!", null, ResponseCode.UPLOAD_SUCCESS);
        } catch (Exception e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage(), ResponseCode.UPLOAD_ERROR);

        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping("/get")
    public ResponseEntity<ResponseMessageDTO> getFiles(@RequestBody FolderDTO folderDTO) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            responseMessageDTO = new ResponseMessageDTO(fileUploadService.getAllFiles(folderDTO.getPath()), "", null, ResponseCode.SUCCESS);
        } catch (Exception e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, "Could not load directory" + ". Error: " + e.getMessage(), ResponseCode.ERROR);

        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }
}
