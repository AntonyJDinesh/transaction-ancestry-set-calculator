package com.ajd.interview.bitgo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
@Data
public class BlockTxn {
    private String txid;
    private int version;
    private long locktime;
    private ArrayList<Vin> vin;
    private ArrayList<Vout> vout;
    private int size;
    private int weight;
    private int fee;
    private Status status;

    @Data
    public static class Prevout{
        private String scriptpubkey;
        private String scriptpubkey_asm;
        private String scriptpubkey_type;
        private String scriptpubkey_address;
        private long value;
    }


    @Data
    public static class Status{
        private boolean confirmed;
        private int block_height;
        private String block_hash;
        private int block_time;
    }

    @Data
    public static class Vin{
        private String txid;
        private long vout;
        private Prevout prevout;
        private String scriptsig;
        private String scriptsig_asm;
        private ArrayList<String> witness;
        @JsonProperty("is_coinbase")
        private boolean is_coinbase;
        private Object sequence;
        private String inner_redeemscript_asm;
        private String inner_witnessscript_asm;
    }

    @Data
    public static class Vout{
        private String scriptpubkey;
        private String scriptpubkey_asm;
        private String scriptpubkey_type;
        private String scriptpubkey_address;
        private long value;
    }
}
