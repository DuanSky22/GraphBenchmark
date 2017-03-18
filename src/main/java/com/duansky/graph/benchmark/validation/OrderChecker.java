package com.duansky.graph.benchmark.validation;

import com.hazelcast.core.HazelcastInstance;

/**
 * Created by SkyDream on 2017/3/18.
 */
public class OrderChecker extends MapResultChecker {

    public OrderChecker(String pathA, HazelcastInstance hi, String name) {
        super(pathA, hi, name);
    }

    @Override
    public double calculateScore() {
        return 0;
    }
}
