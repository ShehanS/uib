package com.uib.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uib.api.dtos.*;
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
import com.uib.api.utilits.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WorkspaceServices implements IWorkspace {
    private WorkspaceRepository workspaceRepository;
    private UserProjectRepository userProjectRepository;

    private DirectoryService directoryService;

    private UserRepository userRepository;

    @Value("${project.path}")
    private String path;

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
        user.setCreatedProject(true);
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
        userRepository.save(user);
        return projectDTO;
    }

    @Override
    public FolderTreeDTO getTreeView(String path, String folderName) throws NotFoundException {
        try {
            if (Validator.isExistFolder(path, folderName)) {
                StringBuilder folderPath = new StringBuilder();
                folderPath.append(path);
                folderPath.append(folderName);
                String contentPath = folderPath.toString();
                File rootFolder = new File(contentPath);
                List<FolderTreeDTO> folderTreeDTOList = new ArrayList<>();
                buildFolderTree(rootFolder, folderTreeDTOList);
                FolderTreeDTO workspaceFolder = folderTreeDTOList.get(0);
                workspaceFolder.setRoot(true);

                return workspaceFolder;
            } else {
                throw new NotFoundException("Folder not found !!!!");
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Flow createFlow(Flow flow) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            StringBuilder filePath = new StringBuilder();
            filePath.append(path);
            filePath.append(flow.getSavePath());
            filePath.append("/");
            File directory = new File(filePath.toString());
            File file = new File(directory, flow.getFileName()+".json");
            objectMapper.writeValue(file, flow);
            System.out.println("DTO object written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing to file: " + e.getMessage());
        }
        return flow;
    }


    private static void buildFolderTree(File folder, List<FolderTreeDTO> folderTreeDTOList) {
        FolderTreeDTO folderTreeDTO = new FolderTreeDTO();
        folderTreeDTO.setName(folder.getName());
        folderTreeDTO.setId(folderTreeDTO.getName());
        folderTreeDTO.setPath(folder.getPath());


        File[] files = folder.listFiles();
        if (files != null) {
            List<FolderTreeDTO> subFolders = new ArrayList<>();
            List<String> filesList = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    buildFolderTree(file, subFolders);
                } else {
                    filesList.add(file.getName());
                }
            }

            folderTreeDTO.setSubFolders(subFolders);
            folderTreeDTO.setFiles(filesList);
        }

        folderTreeDTOList.add(folderTreeDTO);
    }

    private boolean isExistFolder(Long userId, Long workspaceId, String folderName) {
        Optional<UserProject> userProject = userProjectRepository.findByFolderName(userId, workspaceId, folderName);
        if (userProject.isPresent()) {
            return true;
        }
        return false;
    }

}
