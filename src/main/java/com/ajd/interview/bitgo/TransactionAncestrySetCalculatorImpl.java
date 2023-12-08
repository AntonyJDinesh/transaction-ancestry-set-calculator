package com.ajd.interview.bitgo;

import java.util.*;

public class TransactionAncestrySetCalculatorImpl implements TransactionAncestrySetCalculator {

    private final Map<String, Integer> ancestrySetSize;
    public TransactionAncestrySetCalculatorImpl(Map<String, Map<String, Set<String>>> blockTxnParentData) {
        this.ancestrySetSize = new HashMap<>();
        for (var parentData : blockTxnParentData.values())
            buildAncestrySetSize(parentData);
    }

    @Override
    public int getAncestrySetSize(String txnId) {
        return ancestrySetSize.getOrDefault(txnId, 0);
    }

    @Override
    public List<AncestrySetSize> getTopAncestrySetSize(int k) {
        var pq = new PriorityQueue<AncestrySetSize>(Comparator.comparing(AncestrySetSize::ancestrySetSize));

        for(var entry : this.ancestrySetSize.entrySet()) {
            if (k > 0) {
                pq.offer(new AncestrySetSize(entry.getKey(), entry.getValue()));
                k--;
            }

            else if (!pq.isEmpty() && pq.peek().ancestrySetSize() < entry.getValue()) {
                pq.poll();
                pq.offer(new AncestrySetSize(entry.getKey(), entry.getValue()));
            }
        }

        var result = new ArrayList<AncestrySetSize>();
        while (!pq.isEmpty()) result.add(pq.poll());
        Collections.reverse(result);
        return result;
    }


    /**
     * Private Section
     */
    private void buildAncestrySetSize(Map<String, Set<String>> parentData) {
        for (String txnId : parentData.keySet())
            if(!ancestrySetSize.containsKey(txnId)) buildAncestrySetSize(parentData, txnId);
    }

    private int buildAncestrySetSize(Map<String, Set<String>> parentData, String txnId) {
        if (this.ancestrySetSize.containsKey(txnId)) return this.ancestrySetSize.get(txnId);

        int count = 0;
        for (var parent : parentData.get(txnId))
            if (parentData.containsKey(parent)) count += buildAncestrySetSize(parentData, parent) + 1;

        this.ancestrySetSize.put(txnId, count);
        return count;
    }
}
