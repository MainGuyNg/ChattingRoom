package com.mg.utils;

import java.io.*;

public class FileUploadUtil {

    /*
     * 传入参数
     * path      为该项目工程下的保存头像的文件夹
     * filename  为文件名，用于获取文件类型
     * uploader  账号名，将用作文件名
     * */
    public static String getHeadIconPath(String path, String filename, String uploader) {
        //path大概就是F:\apache-tomcat-8.5.35\webapps\ChattingRoom\head_icon_img这个路径
        //不一定是这个路径，根据实际的部署路径可能会变更
        StringBuilder stringBuilder = new StringBuilder(path);
        //获取文件类型
        String fileType = filename.substring(filename.lastIndexOf("."));
        //路径拼接，结果大致为F:\apache-tomcat-8.5.35\webapps\ChattingRoom\head_icon_img\XXXXXX.jpg
        stringBuilder.append("\\").append(uploader).append(fileType);
        return stringBuilder.toString();
    }

    public static void uploadHeadIcon(InputStream inputStream, String sourcePath) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(new File(sourcePath), false);

            byte[] buf = new byte[5 * 1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
                outputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
