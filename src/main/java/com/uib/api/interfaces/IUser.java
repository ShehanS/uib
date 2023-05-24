package com.uib.api.interfaces;

import com.uib.api.dtos.UserProjectDTO;
import com.uib.api.dtos.WorkspaceDTO;
import com.uib.api.entities.User;
import com.uib.api.exceptions.*;

import java.sql.SQLException;
import java.util.List;

public interface IUser {
    User createUser(User user) throws DatabaseSaveException, FoundException, SQLException, IsExistFolderException, CreateFolderException, NotFoundException;

    User loginUser(User user) throws DatabaseSaveException, FoundException, SQLException, IsExistFolderException, CreateFolderException, NotFoundException;

    List<UserProjectDTO> getUserByEmail(String email) throws DatabaseSaveException, NotFoundException;
}
