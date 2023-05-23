package com.uib.api.dtos;

import com.uib.api.entities.User;
import com.uib.api.entities.Workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDTO implements Serializable {

   private User user;
   private Workspace workspace;


}
