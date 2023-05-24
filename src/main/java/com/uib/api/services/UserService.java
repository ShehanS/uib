package com.uib.api.services;

import com.uib.api.dtos.FolderDTO;
import com.uib.api.dtos.UserProjectDTO;
import com.uib.api.dtos.WorkspaceDTO;
import com.uib.api.entities.User;
import com.uib.api.entities.Workspace;
import com.uib.api.enums.FolderType;
import com.uib.api.exceptions.*;
import com.uib.api.interfaces.IUser;
import com.uib.api.querys.CustomUserRepository;
import com.uib.api.repositories.UserRepository;
import com.uib.api.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUser {
    private UserRepository userRepository;
    private DirectoryService directoryService;

    private WorkspaceRepository workspaceRepository;
    private CustomUserRepository customUserRepository;

    @Value("${project.path}")
    private String path;

    public UserService(UserRepository userRepository, DirectoryService directoryService, WorkspaceRepository workspaceRepository, CustomUserRepository customUserRepository) {
        this.userRepository = userRepository;
        this.directoryService = directoryService;
        this.workspaceRepository = workspaceRepository;
        this.customUserRepository = customUserRepository;
    }


    @Override
    @Transactional
    public User createUser(User user) throws FoundException, SQLException, IsExistFolderException, CreateFolderException, NotFoundException, DatabaseSaveException {

        if (isExistUser(user)) {
            throw new FoundException("The email has already been registered!");
        }

        StringBuilder sb = new StringBuilder();
        Workspace workspace = new Workspace();
        FolderDTO workspaceFolderDTO = new FolderDTO();
        String[] emailUser = user.getEmail().split("@");
        String workspaceNameFolder = user.getFirstName() + "_" + user.getLastName() + "_" + emailUser[0];
        sb.append(path);
        sb.append(workspaceNameFolder);
        user.setWorkspacePath(path);
        user.setWorkspaceFolderName(workspaceNameFolder);
        workspaceFolderDTO.setWorkspaceFolderName(workspaceNameFolder);
        workspaceFolderDTO.setFolderType(FolderType.WORKSPACE_FOLDER);
        workspaceFolderDTO.setPath(path);
        workspace.setWorkspaceFolderPath(sb.toString());
        workspace.setWorkspaceFolderName(workspaceNameFolder);
        User savedUser = userRepository.save(user);
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        directoryService.createWorkspaceFolder(workspaceFolderDTO);
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        workspaceDTO.setUser(user);
        workspaceDTO.setWorkspace(savedWorkspace);
        return savedUser;

    }

    @Override
    public User loginUser(User logging) throws NotFoundException {
        User user = userRepository.findByEmail(logging.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    @Transactional
    @Override
    public List<UserProjectDTO> getUserByEmail(String email) throws DatabaseSaveException, NotFoundException {
        List<UserProjectDTO> userProjectDTO = null;
        try {
            userProjectDTO = customUserRepository.findUserProjectsByEmail(email);
        } catch (Exception ex) {
            throw new DatabaseSaveException("Error getting user", ex);
        }
        return userProjectDTO;
    }


    private boolean isExistUser(User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            return true;
        }
        return false;
    }


}
