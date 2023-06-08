package com.uib.api.utilits;

import com.uib.api.dtos.Node;

import java.io.File;

public abstract class Validator {
    public static boolean isExistFolder(String path, String folderName) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if (file.getName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateRequiredField(Node node){
        return true;
    }

}
