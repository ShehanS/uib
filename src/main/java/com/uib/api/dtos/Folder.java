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
public class Folder {
    private String folderName;
    private String rootFolderName;
    private String projectFolderName;
    private FolderType folderType;
    private String path;
}

