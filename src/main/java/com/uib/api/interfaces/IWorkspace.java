package com.uib.api.interfaces;

import com.uib.api.dtos.*;
import com.uib.api.exceptions.CreateFolderException;
import com.uib.api.exceptions.FoundException;
import com.uib.api.exceptions.IsExistFolderException;
import com.uib.api.exceptions.NotFoundException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public interface IWorkspace {
  ProjectDTO createProjectFolder(CreateFolderDTO folder) throws FoundException, NotFoundException, IsExistFolderException, CreateFolderException;

  FolderTreeDTO getTreeView(String path, String folderName) throws NotFoundException;

  Flow createFlow(Flow flow);

  Flow updateFlow(Flow flow) throws NotFoundException, ParserConfigurationException, TransformerException;

  Flow openFlow(Flow flow) throws  NotFoundException;

  String deleteFlow(DeleteItemDTO path) throws NotFoundException, FileNotFoundException;


}
