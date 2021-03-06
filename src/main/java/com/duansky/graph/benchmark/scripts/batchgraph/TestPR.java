package com.duansky.graph.benchmark.scripts.batchgraph;

import com.duansky.graph.benchmark.components.GraphTemplate;
import com.duansky.graph.benchmark.components.impl.DefaultTemplate;
import com.duansky.graph.benchmark.scripts.AbstractScript;
import com.duansky.graph.benchmark.util.Contract;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;
import org.apache.flink.graph.library.PageRank;
import org.apache.flink.types.IntValue;

import java.io.File;

/**
 * Created by SkyDream on 2017/3/17.
 */
public class TestPR extends AbstractScript {

    public static String resPath = Contract.BASE_FOLD + File.separator + "test-page-rank.txt";

    public static String name = "test Page Rank";


    public TestPR(){
        super();
        setResPath(resPath);
        setScriptName(name);
    }

    public TestPR(String templatePath){
        super(templatePath);
        setResPath(resPath);
        setScriptName(name);
    }

    public TestPR(GraphTemplate template){
        super(new GraphTemplate[]{template});
        setResPath(resPath);
        setScriptName(name);
    }


    @Override
    protected String runInternal(GraphTemplate template) throws Exception {
        try {
            //generate the graph of this template.
            Graph graph = graphGenerator.generateGraph(env,
                    graphPathTransformer.getPath(Contract.DATA_FOLDER_GELLY,template),
                    graphPathTransformer.getVertexPath(Contract.DATA_FOLDER_GELLY,template));

            //run algorithm on this graph.
            DataSet<Vertex<IntValue, Double>> calcutateResult =
                    new PageRank<IntValue>(0.2,100).run(graph);
            calcutateResult.print();
            calcutateResult.writeAsCsv(
                    resultPathTransformer.getPath(name,template,Contract.DATA_FOLDER_GELLY),
                    FileSystem.WriteMode.OVERWRITE).setParallelism(1);

            //trigger this algorithm.
            env.execute("single source shortest path");

            //get the job result and its id.
            JobExecutionResult result = env.getLastJobExecutionResult();
            String jobId = result.getJobID().toString();

            return String.format("test for graph(%s,%s)\tjobID:%s\truntime:%s\n",
                    template.getVertexNumber(),
                    template.getProbability(),
                    jobId,
                    result.getNetRuntime());

        } catch (Exception e) {
            e.printStackTrace();

            return String.format("test for graph(%s,%s)\t%s\n",
                    template.getVertexNumber(),
                    template.getProbability(),
                    "Error!");
        }
    }

    public static void main(String args[]) throws Exception{
        TestPR test;
        if(args == null || args.length == 0)
            test = new TestPR();
        else if(args.length == 1)
            test = new TestPR(args[0]);
        else if(args.length == 2)
            test = new TestPR(new DefaultTemplate(Integer.parseInt(args[0]),Double.parseDouble(args[1])));
        else
            throw new IllegalArgumentException("args error for degree distribution test.");
        test.run();
    }
}
