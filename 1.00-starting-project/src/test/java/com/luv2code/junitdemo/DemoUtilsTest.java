package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DemoUtilsTest {

    private DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach(){
        demoUtils = new DemoUtils();
    }

    @Test
    @Order(2)
    @DisplayName("Test Equals Add")
    void test_equals_add() {
        //Given
        int number1 = 5;
        int number2 = 5;

        //When
        int result = demoUtils.add(number1,number2);

        //Then
        assertEquals(result,10,"Should equals 10");
    }

    @Test
    @Order(1)
    void test_equals_multiply() {
        //Given
        int number1 = 5;
        int number2 = 5;

        //When
        int result = demoUtils.multiply(number1,number2);

        //Then
        assertEquals(result,25,"Should equals 25");
    }

    @Test
    void test_same_and_test_should_true() {
        //Given
        String s1= "Oussama";
        String s2= "Oussama";
        boolean isOld = true;
        //When

        //Then
        assertSame(s1,s2,"Should be same");
        assertTrue(isOld,"Should be true");
    }

    @Test
    void test_array_list_equal() {
        //Given
        String[] array1= {"Oussama","Yasser"};
        String[] array2= {"Oussama","Yasser"};

        //When

        //Then
        assertArrayEquals(array1,array2,"arrays Should be same");
        assertIterableEquals(Arrays.asList(array1), Arrays.asList(array2),"lists Should be same");
    }

    @Test
    void test_throw() {
        //Given
        String name = null;

        //When

        //Then
        assertThrows(NullPointerException.class,() -> name.toLowerCase(),"Should throw NullPointeException");
    }

    @Test
    void test_timeout() {
        //Given

        //When

        //Then
        assertTimeoutPreemptively(Duration.ofSeconds(2),() -> Thread.sleep(1000),"Should respect Timeout");
    }
}