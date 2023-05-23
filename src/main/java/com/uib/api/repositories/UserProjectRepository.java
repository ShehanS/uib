package com.uib.api.repositories;

import com.uib.api.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    @Query("SELECT up FROM UserProject up WHERE up.projectFolderName = :folderName AND up.user.id = :userId AND up.workspace.id = :workspaceId")
    Optional<UserProject> findByFolderName(@Param("userId") Long userId, @Param("workspaceId") Long workspaceId, @Param("folderName") String folderName);
}
