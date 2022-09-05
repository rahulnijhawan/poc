package com.rahul.functionalprogramming;

public record ProductRecord(
        int id,
        String name
) {
    public ProductRecord {
        if (name.contains("rahul")) {
            throw new IllegalStateException("Name contains rahul");
        }
    }
}
