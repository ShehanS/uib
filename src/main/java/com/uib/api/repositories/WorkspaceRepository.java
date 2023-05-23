package com.uib.api.repositories;

import com.uib.api.entities.UserProject;
import com.uib.api.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

}
