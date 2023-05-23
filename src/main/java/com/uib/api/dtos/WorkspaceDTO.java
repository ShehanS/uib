package com.uib.api.dtos;

import com.uib.api.entities.User;
import com.uib.api.entities.Workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {
    public User user;
    private Workspace workspace;
}
