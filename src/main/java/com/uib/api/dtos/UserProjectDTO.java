package com.uib.api.dtos;

import com.uib.api.entities.User;
import com.uib.api.entities.Workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserProjectDTO implements Serializable {
    private Long id;
    private String email;
    private String workspacePath;
    private String workspaceFolderName;
    private String workspaceFolderPath;
    private String projectFolderName;
    private String projectFolderPath;
    private boolean createdProject;
}
