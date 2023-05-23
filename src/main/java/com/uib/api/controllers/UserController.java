package com.uib.api.controllers;


import com.uib.api.dtos.FindUserDTO;
import com.uib.api.dtos.ResponseMessageDTO;
import com.uib.api.dtos.UserProjectDTO;
import com.uib.api.dtos.WorkspaceDTO;
import com.uib.api.entities.User;
import com.uib.api.enums.ResponseCode;
import com.uib.api.exceptions.*;
import com.uib.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/webservice/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(path = "create")
    public ResponseEntity<ResponseMessageDTO> addUser(@RequestBody User user) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            WorkspaceDTO createdUser = userService.createUser(user);
            responseMessageDTO = new ResponseMessageDTO(createdUser, null, null, ResponseCode.USER_ADDED_SUCCESS);
        } catch (FoundException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (SQLException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (IsExistFolderException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (CreateFolderException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

    @PostMapping(path = "/find")
    public ResponseEntity<ResponseMessageDTO> findUser(@RequestBody FindUserDTO user) {
        ResponseMessageDTO responseMessageDTO = null;
        try {
            List<UserProjectDTO> projectDTO = userService.getUserByEmail(user.getEmail());
            responseMessageDTO = new ResponseMessageDTO(projectDTO, null, null, ResponseCode.SUCCESS);
        } catch (DatabaseSaveException | NotFoundException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

}
