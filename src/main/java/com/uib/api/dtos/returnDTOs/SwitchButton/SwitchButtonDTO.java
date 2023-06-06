package com.uib.api.dtos.returnDTOs.SwitchButton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwitchButtonDTO {
    private String name;
    private boolean value;
}
