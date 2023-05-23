package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String userImage;
}
