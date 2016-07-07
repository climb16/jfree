package com.jfree.ioc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载器
 * Created by xiao on 2015/9/13.
 *
 * @version 1.0.0
 */
public final class ClassLoader {

    /**
     * 获取类加载器
     *
     * @return
     */
    public static java.lang.ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * 调用Class.forName()方法，加载类，只能调用无参的构造函数
     * @param className     类名
     * @param isInitialized  标志是否执行 静态块
     * @return
     */
    public static Class<?> loadClass(
            final String className, boolean isInitialized) {
        try {
            return Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定包下的所有类
     * @param packageNames
     * @return
     */
    public static Set<Class<?>> getClassSet(String... packageNames) {
        Set<Class<? extends Object>> classSet = new HashSet<>();
        for (String packageName : packageNames) {
            try {
                Enumeration<URL> urls = getClassLoader().
                        getResources(packageName.replace(".", "/"));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    if (url != null) {
                        String protocol = url.getProtocol();
                        if ("file".equals(protocol)) {
                            String packagePath = url.getPath().replaceAll("%20", "");
                            addClass(classSet,  packageName, packagePath);
                        } else if ("jar".equals(protocol)) {

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classSet;
    }

    private static void getJarClassSet(URL url){
        try {
            JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
            if (jarURLConnection != null) {
                JarFile jarFile = jarURLConnection.getJarFile();
                if (jarFile != null) {
                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        JarEntry jarEntry = jarEntries.nextElement();
                        String jarEntityName = jarEntry.getName();
                        if (jarEntityName.endsWith(".class")) {
                            String className = jarEntityName.substring(
                                    0, jarEntityName.lastIndexOf(".")).replaceAll("/", ".");
                            System.out.println(className);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将要加载的类放入容器
     * @param classSet
     * @param packageName
     * @param packagePath
     */
    private static void addClass(
            Set<Class<? extends Object>> classSet, String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.isFile() && file.getName().endsWith(".class");
                }
        });
        for(File file : files){
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if(null != packageName){
                    className = packageName + "." + className;
                    addClass(classSet, className);
                }
            }
        }
    }

    /**
     * 将要加载的类放入容器
     * @param classSet
     * @param className
     */
    private static void addClass(Set<Class<?>> classSet, String className){
        classSet.add(loadClass(className, false));
    }


    public static void main(String[] args) throws IOException {
        Thread t = Thread.currentThread();
        Enumeration<URL> e = t.getContextClassLoader().getResources("com/base/server");
        while (e.hasMoreElements()){
            URL u = e.nextElement();
            //System.out.println(u.getPath() + "---" + u.toString()+"--"+u.getProtocol());
        }
        //System.out.println();
    }
}