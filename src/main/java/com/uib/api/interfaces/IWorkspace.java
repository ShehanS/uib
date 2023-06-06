package com.uib.api.interfaces;

import com.uib.api.dtos.CreateFolderDTO;
import com.uib.api.dtos.Flow;
import com.uib.api.dtos.FolderTreeDTO;
import com.uib.api.dtos.ProjectDTO;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.IsExistFolderException;
import com.uib.api.exceptions.NotFoundException;

public interface IWorkspace {
  ProjectDTO createProjectFolder(CreateFolderDTO folder) throws FoundException, NotFoundException, IsExistFolderException, CreateFolderException;

  FolderTreeDTO getTreeView(String path, String folderName) throws NotFoundException;

  Flow createFlow(Flow flow);

  Flow updateFlow(Flow flow) throws  NotFoundException;

  Flow openFlow(Flow flow) throws  NotFoundException;

}
