package com.uib.api.controllers;


import com.uib.api.dtos.GoogleProfileDTO;
import com.uib.api.dtos.ResponseMessageDTO;
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

@RestController
@RequestMapping("/webservice/api/user")
public class GoogleAuthController {
    private UserService userService;

    public GoogleAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<ResponseMessageDTO> addUser(@RequestBody GoogleProfileDTO profile) throws FoundException, IsExistFolderException, SQLException, CreateFolderException, NotFoundException, DatabaseSaveException {
        ResponseMessageDTO responseMessageDTO = null;
        User user = new User();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        try {
            responseMessageDTO = new ResponseMessageDTO(userService.createUser(user), null, null, ResponseCode.USER_ADDED_SUCCESS);
        } catch (FoundException e) {
            responseMessageDTO = new ResponseMessageDTO(userService.loginUser(user),  e.getMessage(),null, ResponseCode.USER_HAS_CREATED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IsExistFolderException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (CreateFolderException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (NotFoundException e) {
            responseMessageDTO = new ResponseMessageDTO(null, null, e.getMessage(), ResponseCode.ERROR);
        } catch (DatabaseSaveException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(responseMessageDTO);
    }

}
