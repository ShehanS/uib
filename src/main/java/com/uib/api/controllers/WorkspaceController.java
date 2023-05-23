package com.uib.api.controllers;

import com.uib.api.dtos.CreateFolderDTO;
import com.uib.api.dtos.ResponseMessageDTO;
import com.uib.api.dtos.ProjectDTO;
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
            responseMessageDTO = new ResponseMessageDTO(e.getMessage(), null, null, ResponseCode.ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

}
