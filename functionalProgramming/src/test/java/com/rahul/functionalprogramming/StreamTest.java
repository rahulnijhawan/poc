package com.rahul.functionalprogramming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class StreamTest {
    @Test
    void onlyOneTimeConsumeStream() {
        Stream<Integer> stream = List.of(1, 4, 5).stream();
        stream.forEach(System.out::println);
        Assertions.assertThrows(IllegalStateException.class,  () -> stream.forEach(System.out::println));
    }

    @Test
    @Disabled
    @DisplayName("Forever Running Stream")
    void foreverRunningStream() {
        // order of intermediate function matters
        IntStream.iterate(0, i -> (i+1)%2)
                .distinct()
                .limit(10)
                .forEach(System.out::println);

    }

    @Test
    @DisplayName("Not Forever Running Stream")
    void notForeverRunningStream() {
        // order of intermediate function matters
        IntStream.iterate(0, i -> (i+1)%2)
                .limit(10)
                .distinct()
                .forEach(System.out::println);

    }

    @Test
    @Disabled
    @DisplayName("forever Running parallel Stream")
    void foreverRunningParallelStream() {
        IntStream.iterate(0, i -> (i+1)%2)
                .parallel()
                .distinct()
                .limit(10)
                .forEach(System.out::println);

    }
}
