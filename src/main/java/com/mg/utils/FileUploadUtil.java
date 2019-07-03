package com.mg.utils;

public class FileUploadUtil {
    public static String getHeadIconPath(String path,String filename,String uploader){
        StringBuilder stringBuilder = new StringBuilder(path);
        String fileType = filename.substring(filename.lastIndexOf("."));
        stringBuilder.append("\\").append(uploader).append(fileType);
        return stringBuilder.toString();
    }
}
