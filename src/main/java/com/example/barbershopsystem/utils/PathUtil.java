package com.example.barbershopsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtil {
    public static String generateFilePath(String fileName){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd/");
        String dataPath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //找到点的索引
        int index = fileName.lastIndexOf(".");
        //找到文件后缀
        String end = fileName.substring(index);
        return new StringBuilder().append(dataPath).append(uuid).append(end).toString();
    }
}
