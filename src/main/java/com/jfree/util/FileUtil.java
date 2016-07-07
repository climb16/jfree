package com.jfree.util;

/**
 * 文件操作工具类

 * @version 1.0.0
 */
public final class FileUtil {

    /**
     * 从类加载器获取文件路径
     * @param fileName
     * @return filePath
     */
    public static String getFileURL(String fileName){
        return FileUtil.class.getClassLoader().getResource(fileName).getPath();
    }
}