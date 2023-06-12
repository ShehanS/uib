package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteItemDTO {
    private String type;
    private String path;
    private String filePath;
}
