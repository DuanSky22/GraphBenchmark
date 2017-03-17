package com.duansky.graph.benchmark.components.impl;


import com.duansky.graph.benchmark.components.GraphTemplate;
import com.duansky.graph.benchmark.components.PathTransformer;

import java.io.File;

/**
 * Created by SkyDream on 2016/11/1.
 */
public class DefaultPathTransformer implements PathTransformer {

    /**
     * singleton design.
     */
    private static DefaultPathTransformer INSTANCE = new DefaultPathTransformer();
    public static DefaultPathTransformer getInstance(){
        return INSTANCE;
    }
    private DefaultPathTransformer(){}

    @Override
    public String getVertexPath(String folder, GraphTemplate template) {
        return getPath(folder,template)+"-verities.txt";
    }

    @Override
    public String getEdgePath(String folder, GraphTemplate template) {
        return getPath(folder,template)+"-edges.txt";
    }

    @Override
    public String getPath(String folder,GraphTemplate template){
        return String.format("%s%sgraph-%s-%s",
                folder,
                File.separator,
                template.getVertexNumber(),
                template.getProbability());
    }

    @Override
    public String getPath(String folder, GraphTemplate template, int number) {
        return String.format("%s%sgraph-%s-%s-%s",
                folder,
                File.separator,
                template.getVertexNumber(),
                template.getProbability(),
                number);
    }
}
