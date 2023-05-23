package com.uib.api.services;

import com.uib.api.dtos.CreateFolderDTO;
import com.uib.api.dtos.FolderDTO;
import com.uib.api.dtos.ProjectDTO;
import com.uib.api.entities.User;
import com.uib.api.entities.UserProject;
import com.uib.api.entities.Workspace;
import com.uib.api.enums.FolderType;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.IsExistFolderException;
import com.uib.api.exceptions.NotFoundException;
import com.uib.api.interfaces.IWorkspace;
import com.uib.api.repositories.UserProjectRepository;
import com.uib.api.repositories.UserRepository;
import com.uib.api.repositories.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class WorkspaceServices implements IWorkspace {
    private WorkspaceRepository workspaceRepository;
    private UserProjectRepository userProjectRepository;

    private DirectoryService directoryService;

    private UserRepository userRepository;

    public WorkspaceServices(WorkspaceRepository workspaceRepository, UserProjectRepository userProjectRepository, UserRepository userRepository, DirectoryService directoryService) {
        this.workspaceRepository = workspaceRepository;
        this.userProjectRepository = userProjectRepository;
        this.userRepository = userRepository;
        this.directoryService = directoryService;
    }

    @Transactional
    @Override
    public ProjectDTO createProjectFolder(CreateFolderDTO folder) throws FoundException, NotFoundException, IsExistFolderException, CreateFolderException {
        if (isExistFolder(folder.getUserId(), folder.getWorkspaceId(), folder.projectFolderName)) {
            throw new FoundException("This name already existed");
        }
        StringBuilder projectFolderPath = new StringBuilder();
        projectFolderPath.append(folder.getPath());
        projectFolderPath.append("/");
        UserProject userProject = new UserProject();
        Workspace workspace = workspaceRepository.findById(folder.getWorkspaceId()).orElseThrow(() -> new NotFoundException("Workspace not found"));
        User user = userRepository.findById(folder.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        FolderDTO projectFolder = new FolderDTO();
        projectFolder.setProjectFolderName(folder.getProjectFolderName());
        projectFolder.setPath(workspace.getWorkspaceFolderPath());
        projectFolder.setFolderType(FolderType.PROJECT_FOLDER);
        FolderDTO createdFolder = directoryService.createFolder(projectFolder);
        userProject.setWorkspace(workspace);
        userProject.setUser(user);
        userProject.setProjectFolderPath(createdFolder.getPath());
        userProject.setProjectFolderName(folder.getProjectFolderName());
        userProjectRepository.save(userProject);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setUser(user);
        projectDTO.setWorkspace(workspace);
        return projectDTO;
    }


    private boolean isExistFolder(Long userId, Long workspaceId, String folderName) {
        Optional<UserProject> userProject = userProjectRepository.findByFolderName(userId, workspaceId, folderName);
        if (userProject.isPresent()) {
            return true;
        }
        return false;
    }

}
