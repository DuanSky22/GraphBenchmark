package com.duansky.graph.benchmark.components.impl;

import com.duansky.graph.benchmark.components.GraphMerger;
import com.duansky.graph.benchmark.components.GraphTemplate;
import com.duansky.graph.benchmark.components.PathTransformer;
import com.duansky.graph.benchmark.util.Files;

import java.io.*;

/**
 * Created by SkyDream on 2017/3/17.
 */
public class DefalutGraphMerger implements GraphMerger {

    private PathTransformer pathTransformer = DefaultPathTransformer.getInstance();

    private static final DefalutGraphMerger INSTANCE = new DefalutGraphMerger();
    public static DefalutGraphMerger getInstance(){return INSTANCE;}

    @Override
    public void merge(String folder, GraphTemplate template) {
        //get all the splits prefix,also as the integrated file name.
        final String prefix = pathTransformer.getPath(folder,template);
        //find all the splits.
        File base = new File(folder);
        File[] parts = base.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getPath().startsWith(prefix);
            }
        });
        PrintWriter writer = Files.asPrintWriter(prefix);
        //merge the splits.
        BufferedReader reader;
        try {
            for(File part : parts){
                reader = Files.asBufferedReader(part);
                String temp = "";
                while(temp != null) {
                    temp = reader.readLine();
                    if(temp != null) writer.write(temp);
                }
                writer.flush();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
        System.out.println("merge "+template+" done!");
    }
}