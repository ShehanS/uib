package com.uib.api.repositories;

import com.uib.api.entities.User;
import com.uib.api.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query("SELECT up FROM UserProject up JOIN up.user u WHERE u.email = :email")
    List<UserProject> findByUserEmail(@Param("email") String email);
}
