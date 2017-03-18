package com.duansky.graph.benchmark.driver;

/**
 * Created by SkyDream on 2017/3/18.
 */
public class TestEngine {

    //(algorithmName,vertexNumber,possibility)
    public static void main(String args[]){
        String name = System.currentTimeMillis()+"";
        if(args != null && args.length == 3){
            //start batch test.
            BatchTestDriver.main(args);
            //start stream test.
            StreamTestDriver.main(args);
            //start check
            ResultCheckDriver.main(args);
        }

    }

}
