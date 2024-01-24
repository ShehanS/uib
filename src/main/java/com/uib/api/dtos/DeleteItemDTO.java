package com.uib.api.dtos;

import com.uib.api.enums.ResponseCode;
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
    private ResponseCode responseCode;
}
