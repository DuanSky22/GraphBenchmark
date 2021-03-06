package com.duansky.graph.benchmark.components.impl;


import com.duansky.graph.benchmark.components.GraphGenerator;
import com.duansky.graph.benchmark.components.GraphTemplate;
import com.duansky.graph.benchmark.util.Graphs;
import com.duansky.graph.benchmark.util.Maths;
import org.apache.commons.math3.util.DoubleArray;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.types.DoubleValue;
import org.apache.flink.types.IntValue;
import org.apache.flink.types.NullValue;
import org.apache.log4j.Logger;

/**
 * Created by DuanSky on 2016/10/30.
 */
public class DefaultGraphGenerator implements GraphGenerator {

    Logger logger = Logger.getLogger(DefaultGraphGenerator.class);

    private static DefaultGraphGenerator INSTANCE = new DefaultGraphGenerator();

    public static DefaultGraphGenerator getInstance(){
        return INSTANCE;
    }

    private DefaultGraphGenerator(){}

    public Graph generateGraph(ExecutionEnvironment env,GraphTemplate template) {

        logger.info(String.format("start generate graph(%s,%s)",
                template.getVertexNumber(),
                template.getProbability()));

        if(template == null)
            throw new IllegalArgumentException("the templete must be inited by using DefaultTemplate first!");
        int n = template.getVertexNumber();
        double p = template.getProbability();
        //the random edge.
        int[][] e = Maths.getRandomUndirectedPairs(n, (int)(Maths.getCombinationsNumber(n,2) * p));
        DataSet<Edge<IntValue,NullValue>> edges = Graphs.transform(env,e);

        logger.info(String.format("generate graph(%s,%s) done!",
                template.getVertexNumber(),
                template.getProbability()));

        return Graph.fromDataSet(edges,env);
    }

    @Override
    public Graph generateGraph(ExecutionEnvironment env, String edgePath,String vertexPath) {
        return Graph.fromCsvReader(vertexPath,edgePath,env).types(IntValue.class,Double.class,Double.class);
    }

    @Override
    public Graph generateGraph(ExecutionEnvironment env, String edgePath) {
        return Graph.fromCsvReader(edgePath,env).edgeTypes(IntValue.class,Double.class);
    }
}
