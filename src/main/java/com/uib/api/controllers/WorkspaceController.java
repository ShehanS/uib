package com.uib.api.controllers;

import com.uib.api.dtos.*;
import com.uib.api.enums.ResponseCode;
import com.uib.api.services.WorkspaceServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/webservice/api/workspace")
public class WorkspaceController {
    private WorkspaceServices workspaceServices;

    public WorkspaceController(WorkspaceServices workspaceServices) {
        this.workspaceServices = workspaceServices;
    }

    @PostMapping(path = "/createProject")
    public ResponseEntity<ResponseMessageDTO> createProjectFolder(@RequestBody CreateFolderDTO createFolderDTO) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            ProjectDTO projectDTO = workspaceServices.createProjectFolder(createFolderDTO);
            responseMessageDTO = new ResponseMessageDTO(projectDTO, null, null, ResponseCode.SUCCESS);
        } catch (Exception e) {
            responseMessageDTO = new ResponseMessageDTO(null, e.getMessage(), null, ResponseCode.ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/treeView")
    public ResponseEntity<ResponseMessageDTO> getTreeView(@RequestBody CreateFolderDTO createFolderDTO) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            FolderTreeDTO folderTreeDTO = workspaceServices.getTreeView(createFolderDTO.path, createFolderDTO.getWorkspaceFolderName());
            responseMessageDTO = new ResponseMessageDTO(folderTreeDTO, null, null, ResponseCode.SUCCESS);
        } catch (Exception e) {
            responseMessageDTO = new ResponseMessageDTO(e.getMessage(), null, null, ResponseCode.ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/createFlow")
    public ResponseEntity<ResponseMessageDTO> createFlow(@RequestBody Flow flow) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            responseMessageDTO = new ResponseMessageDTO(workspaceServices.createFlow(flow), null, null, ResponseCode.FILE_CREATED_SUCCESS);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/openFlow")
    public ResponseEntity<ResponseMessageDTO> fileOpen(@RequestBody Flow flow) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            responseMessageDTO = new ResponseMessageDTO(workspaceServices.openFlow(flow), null, null, ResponseCode.FILE_OPEN_SUCCESS);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/updateFlow")
    public ResponseEntity<ResponseMessageDTO> updateFlow(@RequestBody Flow flow) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            responseMessageDTO = new ResponseMessageDTO(workspaceServices.updateFlow(flow), null, null, ResponseCode.FILE_CREATED_SUCCESS);
        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<ResponseMessageDTO> deleteFile(@RequestBody DeleteItemDTO deleteItemDTO) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            responseMessageDTO = new ResponseMessageDTO(workspaceServices.deleteFlow(deleteItemDTO), null, null, ResponseCode.FILE_DELETE_SUCCESS);
        } catch (Exception e) {
            responseMessageDTO = new ResponseMessageDTO(null, e.getMessage(), null, ResponseCode.FILE_DELETE_ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }


}
