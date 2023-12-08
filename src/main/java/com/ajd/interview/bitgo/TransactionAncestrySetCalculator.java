package com.ajd.interview.bitgo;

import java.util.List;

public interface TransactionAncestrySetCalculator {
    int getAncestrySetSize(String txnId);
    List<AncestrySetSize> getTopAncestrySetSize(int k);

    public static record AncestrySetSize(String txnId, int ancestrySetSize) { }
}
