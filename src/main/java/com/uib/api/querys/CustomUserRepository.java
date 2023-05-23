package com.uib.api.querys;


import com.uib.api.dtos.ProjectDTO;
import com.uib.api.dtos.UserProjectDTO;
import com.uib.api.entities.User;
import com.uib.api.entities.UserProject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomUserRepository {
    private final EntityManager entityManager;

    public CustomUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<UserProjectDTO> findUserProjectsByEmail(String email) {
        TypedQuery<UserProject> query = entityManager.createQuery("SELECT up FROM UserProject up JOIN FETCH up.user u WHERE u.email = :email", UserProject.class);
        query.setParameter("email", email);
        List<UserProject> userProjects = query.getResultList();

        List<UserProjectDTO> userProjectDTOs = new ArrayList<>();
        for (UserProject userProject : userProjects) {
            UserProjectDTO dto = new UserProjectDTO();
            dto.setId(userProject.getId());
            dto.setEmail(userProject.getUser().getEmail());
            dto.setProjectFolderName(userProject.getProjectFolderName());
            dto.setProjectFolderPath(userProject.getProjectFolderPath());
            dto.setWorkspacePath(userProject.getUser().getWorkspacePath());
            dto.setWorkspaceFolderName(userProject.getUser().getWorkspaceFolderName());
            dto.setWorkspaceFolderPath(userProject.getUser().getWorkspacePath()+userProject.getUser().getWorkspaceFolderName());
            userProjectDTOs.add(dto);
        }

        return userProjectDTOs;
    }
}
