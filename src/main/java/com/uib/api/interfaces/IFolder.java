package com.uib.api.interfaces;

import com.uib.api.dtos.FolderDTO;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.IsExistFolderException;

public interface IFolder {
    FolderDTO createFolder(FolderDTO folderDTOName) throws CreateFolderException, IsExistFolderException;

    FolderDTO createWorkspaceFolder(FolderDTO folderDTOName) throws CreateFolderException, IsExistFolderException;

}
