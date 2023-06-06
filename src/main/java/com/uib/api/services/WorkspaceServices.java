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
import factory.IInputType;
import factory.InputFactory;
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
        Flow saveFlow = new Flow();
        try {
            StringBuilder filePath = new StringBuilder();
            filePath.append(flow.getSavePath());
            File projectPath = new File(flow.getSavePath());
            String currentFolder = projectPath.getParent();
            File projectFolder = new File(currentFolder);
            filePath.append("/");
            File directory = new File(filePath.toString());
            File file = new File(directory, flow.getFileName() + ".json");
            objectMapper.writeValue(file, flow);
            saveFlow.setFileName(flow.getFileName());
            saveFlow.setProjectPath(projectFolder.getPath());
            saveFlow.setSavePath(file.getPath());
            saveFlow.setDisplayName(flow.getDisplayName());
            saveFlow.setDiagram(flow.getDiagram());
            saveFlow.setImage(flow.getImage());
            saveFlow.setValues(flow.getValues());
            saveFlow.setOpenPath(flow.getOpenPath());
        } catch (IOException e) {
        }
        return saveFlow;
    }

    @Override
    public Flow updateFlow(Flow flow) throws NotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            File file = new File(flow.getSavePath());
            objectMapper.writeValue(file, flow);
            List<Node> nodes = flow.getDiagram().getNodes();
            for (Node node : nodes) {
                List<Property> properties = node.getProperties();
                for (Property property : properties) {
                    InputFactory inputFactory = new InputFactory();
                    IInputType input = inputFactory.extractInput(property.getType());
                    System.out.println(input.extract(property));
                }

            }
        } catch (IOException e) {
        }
        return flow;
    }

    @Override
    public Flow openFlow(Flow flow) throws NotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        Flow restoreFlow = null;
        try {

            File file = new File(flow.getOpenPath());
            restoreFlow = objectMapper.readValue(file, Flow.class);
            restoreFlow.setSavePath(file.getPath());
            File flowFolder = new File(flow.getOpenPath());
            File projectFolder = new File(flowFolder.getParent());
            restoreFlow.setProjectPath(projectFolder.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restoreFlow;
    }


    private static void buildFolderTree(File folder, List<FolderTreeDTO> folderTreeDTOList) {
        FolderTreeDTO folderTreeDTO = new FolderTreeDTO();
        folderTreeDTO.setName(folder.getName());
        folderTreeDTO.setId(folderTreeDTO.getName());
        folderTreeDTO.setPath(folder.getPath());


        File[] files = folder.listFiles();
        if (files != null) {
            List<FolderTreeDTO> subFolders = new ArrayList<>();
            List<FileDTO> filesList = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    buildFolderTree(file, subFolders);
                } else {
                    FileDTO f = new FileDTO();
                    f.setFileName(file.getName());
                    f.setFilePath(file.getPath());
                    filesList.add(f);
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
