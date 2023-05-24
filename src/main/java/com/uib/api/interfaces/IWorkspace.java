package com.uib.api.interfaces;

import com.uib.api.dtos.*;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.IsExistFolderException;
import com.uib.api.exceptions.NotFoundException;

public interface IWorkspace {
  ProjectDTO createProjectFolder(CreateFolderDTO folder) throws FoundException, NotFoundException, IsExistFolderException, CreateFolderException;

  FolderTreeDTO getTreeView(String path, String folderName) throws NotFoundException;

  Flow createFlow(Flow flow);

}
