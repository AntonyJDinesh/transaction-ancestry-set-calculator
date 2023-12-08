package com.ajd.interview.bitgo;

import lombok.Data;

@Data
public class Block {
    private String id;
    private int height;
    private int version;
    private int timestamp;
    private int tx_count;
    private int size;
    private int weight;
    private String merkle_root;
    private String previousblockhash;
    private int mediantime;
    private long nonce;
    private int bits;
    private long difficulty;
}
