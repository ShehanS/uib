package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FolderTreeDTO {
    private String type = "folder";
    private String id;
    private String name;
    private String path;
    private boolean root;
    private List<FolderTreeDTO> subFolders;
    private List<FileDTO> files;
}
