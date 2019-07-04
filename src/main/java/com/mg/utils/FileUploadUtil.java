package com.mg.utils;

import java.io.*;

public class FileUploadUtil {
    public static String getHeadIconPath(String path, String filename, String uploader) {
        StringBuilder stringBuilder = new StringBuilder(path);
        String fileType = filename.substring(filename.lastIndexOf("."));
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
        }
    }
}
