package com.uib.api.dtos;


import com.uib.api.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO implements Serializable {
    protected Object data;
    protected String message;
    protected Object error;
    protected ResponseCode code = ResponseCode.SUCCESS;

    public static ResponseMessageDTO getInstance(Object response, String message, Object error, ResponseCode code){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO(response, null, null, code);
        return responseMessageDTO;
    }

    public static ResponseMessageDTO getInstance(String message, Object error, ResponseCode code){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO(null, message, null, code);
        return responseMessageDTO;
    }

    public static ResponseMessageDTO getInstance(String error, ResponseCode code){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO(null, null, error, code);
        return responseMessageDTO;
    }
}
