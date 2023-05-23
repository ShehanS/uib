package com.uib.api.dtos;


import com.uib.api.enums.FolderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDTO {
    private String folderName;
    private String workspaceFolderName;
    private String projectFolderName;
    private FolderType folderType;
    private String path;
    public int userId;
    public int workspaceId;
}

