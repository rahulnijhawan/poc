package com.rahul.functionalprogramming;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
// TBL: Test base learning
public class OptionalTest {

    private String testForElse = "foo";

    public static void main(String[] args) {
    }

    @Test
    @DisplayName("Else Run Always")
    void elseRunAlways() {
        Optional.of(testForElse)
                .orElse(runElse());
        assertEquals("else", testForElse);
    }

    private String runElse() {
        System.out.println("run else function");
        testForElse = "else";
        return testForElse;
    }

    @Test
    @DisplayName("Else not Run Always")
    void elseNotRun() {
        Optional.of(testForElse)
                .orElseGet(() -> runElse());
        assertEquals("foo", testForElse);
    }
}
