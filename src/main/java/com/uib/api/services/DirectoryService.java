package com.uib.api.services;

import com.uib.api.dtos.FolderDTO;
import com.uib.api.enums.FolderType;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.IsExistFolderException;
import com.uib.api.interfaces.IFolder;
import com.uib.api.utilits.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DirectoryService implements IFolder {
    @Value("${project.path}")
    private String path;

    @Override
    public FolderDTO createFolder(FolderDTO folderDTO) throws CreateFolderException, IsExistFolderException {
        try {
            StringBuilder path = new StringBuilder();
            String folderName = folderDTO.getFolderType() == FolderType.PROJECT_FOLDER ? folderDTO.getProjectFolderName() : folderDTO.getFolderName();
            String currentPath = folderDTO.getPath();
            FolderDTO createdFolder = new FolderDTO();
            if (!Validator.isExistFolder(currentPath, folderName)) {
                path.append(currentPath);
                path.append("/");
                path.append(folderName);
                String newFolder = path.toString();
                File file = new File(newFolder);
                boolean isCreated = file.mkdir();
                if (!isCreated) {
                    throw new CreateFolderException("Cannot create folderDTO in the system!!!");
                } else {
                    createdFolder.setPath(file.getPath());
                    createdFolder.setProjectFolderName(path.toString());
                    createdFolder.setWorkspaceFolderName(currentPath);
                    return createdFolder;
                }
            } else {
                throw new IsExistFolderException("This name is already used!!!!");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FolderDTO createWorkspaceFolder(FolderDTO folderDTO) throws CreateFolderException, IsExistFolderException {
        StringBuilder path = new StringBuilder();
        try {
            String folderName = folderDTO.getFolderType() == FolderType.WORKSPACE_FOLDER ? folderDTO.getWorkspaceFolderName() : folderDTO.getFolderName();
            String currentPath = folderDTO.getPath();
            if (!Validator.isExistFolder(currentPath, folderName)) {
                path.append(currentPath);
                path.append("/");
                path.append(folderName);
                String newFolder = path.toString();
                File file = new File(newFolder);
                boolean isCreated = file.mkdir();
                if (!isCreated) {
                    throw new CreateFolderException("Cannot create folderDTO in the system!!!");
                } else {
                    return null;
                }
            } else {
                throw new IsExistFolderException("This name is already used!!!!");
            }
        } catch (Exception e) {
            return null;
        }
    }
}
