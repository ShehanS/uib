package com.uib.api.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uib.api.entities.User;
import com.uib.api.entities.Workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFolderDTO implements Serializable {
    public String workspaceFolderName;
    public String projectFolderName;
    public String folderType;
    public String path;
    public Long userId;
    private Long workspaceId;
}
