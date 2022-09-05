package com.rahul.functionalprogramming;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalInterfaceTest {



    @FunctionalInterface
    interface CheckFunction<T,R> {
        R apply(T t) throws Exception;
    }

    public static <T,R>Function<T,R> tryWrap(CheckFunction<T,R> func) {
        return t ->  {
            try {
                System.out.println("t = " + t);
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

    }

    @Test
    @DisplayName("Check exception wrap in functionInterface")
    void checkExceptionWrapInFunctionInterface() {

        Assertions.assertAll(
                () -> assertThrows(
                        RuntimeException.class,
                        ()-> tryWrap(i -> (int) i%0).apply(0)
                ),
                () -> assertEquals("R", tryWrap(i -> ((String)i).toUpperCase(Locale.ROOT)).apply("r"))
        );
    }
}
