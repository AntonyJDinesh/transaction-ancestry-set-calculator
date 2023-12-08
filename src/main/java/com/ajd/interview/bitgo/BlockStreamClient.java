package com.ajd.interview.bitgo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class BlockStreamClient {
    private static final String BLOCK_HEIGHT_URL = "https://blockstream.info/api/blocks/%s";
    private static final String BLOCK_TXN_URL = "https://blockstream.info/api/block/%s/txs/%d";

    private final String blockHeightId;
    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    private Map<String, Map<String, Set<String>>> blockWiseTxnParentData;

    public BlockStreamClient(String blockHeightId, HttpClient httpClient) {
        this.blockHeightId              = blockHeightId;
        this.httpClient                 = httpClient;
        this.objectMapper               = new ObjectMapper();
        this.blockWiseTxnParentData     = new HashMap<>();

        init();
    }

    public Map<String, Map<String, Set<String>>> getTxnParentData() { return this.blockWiseTxnParentData; }

    /**
     * Private section
     */
    private void init() {
        var blocks = getBlocks();
        for (var block : blocks) {
            if(!this.blockHeightId.equals(String.valueOf(block.getHeight()))) continue;

            Map<String, Set<String>> txnParentData = new HashMap<>();

            for (int startIndex = 0; ; startIndex += 25) {
                System.out.println("startIndex: " + startIndex);
                var blockTxns = getBlockTxn(block.getId(), startIndex);
                if (blockTxns == null) break;
                buildParentData(txnParentData, blockTxns);
            }

            this.blockWiseTxnParentData.put(block.getId(), txnParentData);
        }
    }

    private void buildParentData(Map<String, Set<String>> txnParentData, BlockTxn[] blockTxns) {
        for (var blockTxn : blockTxns) {
            txnParentData.putIfAbsent(blockTxn.getTxid(), new HashSet<>());
            var parentSet = txnParentData.get(blockTxn.getTxid());
            for (var vin : blockTxn.getVin()) parentSet.add(vin.getTxid());
        }
    }

    private Block[] getBlocks() {
        var blockHeightContent = this.httpClient.get(String.format(BLOCK_HEIGHT_URL, this.blockHeightId));
        try {
            return this.objectMapper.readValue(blockHeightContent, Block[].class);
        } catch (Exception e) {
            throw new RuntimeException("unable to parse blockHeightContent", e);
        }
    }

    private BlockTxn[] getBlockTxn(String blockId, int startIndex) {
        var blockHeightContent = this.httpClient.get(String.format(BLOCK_TXN_URL, blockId, startIndex));
        if (blockHeightContent.contains("start index out of range")) return null;

        try {
            return this.objectMapper.readValue(blockHeightContent, BlockTxn[].class);
        } catch (Exception e) {
            throw new RuntimeException("unable to parse blockHeightContent: data " + blockHeightContent, e);
        }
    }
}
