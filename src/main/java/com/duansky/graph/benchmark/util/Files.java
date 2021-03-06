package com.duansky.graph.benchmark.util;

import java.io.*;

/**
 * Created by SkyDream on 2016/11/1.
 */
public class Files {

    /**
     * get the {@link PrintWriter} of this path.
     * @param path the PrintWriter you want to create to.
     * @return the PrintWriter of your given path.
     */
    public static PrintWriter asPrintWriter(String path){
        return asPrintWriter(new File(path));
    }

    public static PrintWriter asPrintWriter(File file){
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            return new PrintWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeAsTxt(String path,String data){
        PrintWriter writer = asPrintWriter(path);
        writer.write(data);
        writer.close();
    }

    public static BufferedReader asBufferedReader(File file){
        try {
            if(!file.exists()) {
                throw new IllegalArgumentException("the "+ file + " is not exists!");
            }
            return new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedReader asBufferedReader(String path){
        return asBufferedReader(new File(path));
    }

    /**
     * check this folder exists. if not create a new folder.
     * @param folder the folder that will be checked.
     * @return true if this folder alreay exists, otherwise false.
     */
    public static boolean checkAndCreateFolder(String folder){
        File file = new File(folder);
        if(!file.exists()){
            file.mkdir();
            return false;
        }
        return true;
    }
}
