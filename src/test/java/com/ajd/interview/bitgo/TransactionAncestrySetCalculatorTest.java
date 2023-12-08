package com.ajd.interview.bitgo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransactionAncestrySetCalculatorTest {

    @Test
    public void getAncestrySetSizeTest() {
        var parentData = new HashMap<String, Set<String>>();

        var set1 = new HashSet<String>();
        set1.add("2");
        set1.add("3");
        set1.add("4");
        parentData.put("1", set1);

        var set2 = new HashSet<String>();
        set2.add("5");
        set2.add("6");

        parentData.put("2", set2);
        parentData.put("5", new HashSet<>());

        Map<String, Map<String, Set<String>>> blockData = new HashMap<>();
        blockData.put("blockID1", parentData);

        var transactionAncestrySetCalculator = new TransactionAncestrySetCalculatorImpl(blockData);
        Assertions.assertEquals(1, transactionAncestrySetCalculator.getAncestrySetSize("2"));
        Assertions.assertEquals(2, transactionAncestrySetCalculator.getAncestrySetSize("1"));

        var res = transactionAncestrySetCalculator.getTopAncestrySetSize(2);
        System.out.println(res);
    }
}
