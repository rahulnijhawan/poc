package com.rahul.functionalprogramming;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import javax.transaction.NotSupportedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRecordTest {

    @BeforeAll
    public static void runBeforeAll() {
        System.out.println("runBeforeAll");
    }

    @BeforeEach
    public void runBeforeEach() {
        System.out.println("runBeforeEach");
    }


    @Test
    @DisplayName("this will fail")
    public void createProductFail() {
        ProductRecord rahul = new ProductRecord(1, "ahul1");
        Assertions.assertEquals("ahul11", rahul.name());
    }

    @Test
    @Disabled("not yet implemented.")
    public void createProductDisable() {
        ProductRecord rahul = new ProductRecord(1, "ahul1");
        Assertions.assertEquals("ahul11", rahul.name());
    }

    @Test
    public void createProductPass() {
        ProductRecord rahul = new ProductRecord(1, "ahul1");
        Assertions.assertEquals("ahul1", rahul.name());
    }


    @Test
    @DisplayName("using all assertions instead of assertEquals which not " +
            "allow other assertion to run after it fail.")
    void allAssertions() {
        List<Integer> l = List.of(1, 2, 3, 4);
        Assertions.assertAll(() -> assertEquals(2, l.get(0)),
                () -> assertEquals(2, l.get(1))
        );
    }

    @Test
    @DisplayName("should Only RunT he Test If Some Criteria Are Met")
    void shouldOnlyRunTheTestIfSomeCriteriaAreMet() {
        Assumptions.assumeTrue(true);
        assertEquals(1, 1);
    }

    @ParameterizedTest(name = "{0} he")
    @ValueSource(ints = {3, 4})
    void parameterizedTest(int expectedValue) {
        assertEquals(expectedValue, expectedValue);
    }

    @ParameterizedTest
    @DisplayName("shouldThrowException")
    @ValueSource(ints = {0, 1})
    void shouldThrowException(int expectedValue) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    throw new NotSupportedException("erroor");
                });
    }

    @Nested
    @DisplayName("Group class with pass and fail tests")
    class GroupTests {

        @Nested
        @DisplayName("Should pass")
        class ShouldPass {

            @Test
            @DisplayName("passTest")
            void passTest() {
             assertEquals(1,1);
            }

        }

        @Nested
        @DisplayName("Should fail")
        class ShouldFail {
            @Test
            @DisplayName("failTest")
            void failTest() {
                assertEquals(1, 0);
            }
        }
    }
}