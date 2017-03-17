package com.duansky.graph.benchmark.driver;

import com.duansky.graph.benchmark.components.impl.DefaultPathTransformer;
import com.duansky.graph.benchmark.components.impl.DefaultTemplate;
import com.duansky.graph.benchmark.util.Contract;
import com.duansky.hazelcast.graphflow.lib.DegreeDistribution;
import com.duansky.hazelcast.graphflow.lib.PageRank;
import com.duansky.hazelcast.graphflow.lib.SSSP;
import com.duansky.hazelcast.graphflow.lib.TriangleCount;

/**
 * Created by SkyDream on 2017/3/18.
 */
public class StreamTestDriver {
    public static void main(String[] args) {
        args = new String[]{"test1","pr","1000","0.2","1"};
        if(args != null && args.length == 5){
            String testName = args[0];
            String algorithmName = args[1];
            Integer vertexNumber = Integer.parseInt(args[2]);
            Double possibility = Double.parseDouble(args[3]);
            int part = Integer.parseInt(args[4]);
            String path = DefaultPathTransformer.getInstance().getPath(
                    Contract.DATA_FOLDER_GELLY,
                    new DefaultTemplate(vertexNumber,possibility),
                    part);
            if(algorithmName.contains("dd")){
                DegreeDistribution<Integer,Double> dd = new DegreeDistribution<Integer, Double>(testName,path,Integer.class,Double.class);
                dd.run();
            } else if(algorithmName.contains("tc")){
                TriangleCount<Integer,Double,Double> tc = new TriangleCount<Integer, Double,Double>(testName,path,Integer.class,Double.class);
                tc.run();
            }else if(algorithmName.contains("sssp")){
                SSSP<Integer,Double> sssp = new SSSP<Integer, Double>(testName,path,0,true,Integer.class,Double.class);
                sssp.run();
            }else if(algorithmName.contains("pr")){
                PageRank<Integer,Double> pr = new PageRank<Integer, Double>(testName,path,Integer.class,Double.class,0.2,100);
                pr.run();
            }
        }else{
            System.out.println("Usage: (test name | algorithm name | vertex number | possibility | part)");
        }
    }
}
