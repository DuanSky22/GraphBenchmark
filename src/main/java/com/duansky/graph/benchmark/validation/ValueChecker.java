package com.duansky.graph.benchmark.validation;

import com.hazelcast.core.HazelcastInstance;

import java.util.Map;

/**
 * Created by SkyDream on 2017/3/18.
 */
public class ValueChecker extends MapResultChecker {

    public ValueChecker(String pathA, HazelcastInstance hi, String name) {
        super(pathA, hi, name);
    }

    @Override
    public double calculateScore() {
        int total = resA.size();
        int correct = 0;
        for(Map.Entry<Integer,Integer> tuple : resA.entrySet()){
            Integer key = tuple.getKey();
            if(resB.containsKey(key)){
                Integer res = Integer.parseInt(resB.get(key).toString());
                if(res.equals(tuple.getValue()))
                    correct++;
            }


        }
        return ((double) correct) / total;
    }
}
