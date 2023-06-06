package com.uib.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class FileUploadDTO {
    private long uploadDate = new Date().getTime();
    private String fileName;
    private String workspaceId;
    private String user;
    private String path;
    private String status;
    private String contentType;
}
