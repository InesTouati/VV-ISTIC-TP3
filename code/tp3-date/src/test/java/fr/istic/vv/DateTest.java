package fr.istic.vv;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    private static Stream<Arguments> daysOutOfMonthBound() {
        return Stream.of(
                Arguments.of(-10, 9, 2015),
                Arguments.of(0, 9, 2015),
                //months of 30 days
                Arguments.of(31, 4, 2015),
                Arguments.of(31, 6, 2015),
                Arguments.of(31, 9, 2015),
                Arguments.of(31, 11, 2015),
                //mont of 31 days
                Arguments.of(32, 1, 2015),
                Arguments.of(32, 3, 2015),
                Arguments.of(32, 5, 2015),
                Arguments.of(32, 7, 2015),
                Arguments.of(32, 8, 2015),
                Arguments.of(32, 10, 2015),
                Arguments.of(32, 12, 2015),
                Arguments.of(30,2,2015)
        );
    }

    private static Stream<Arguments> daysInMonthBound() {
        return Stream.of(
                Arguments.of(1, 1, 2015),
                // months of 30 days
                Arguments.of(30, 4, 2015),
                Arguments.of(30, 6, 2015),
                Arguments.of(30, 9, 2015),
                Arguments.of(30, 11, 2015),
                // moths of 31 days
                Arguments.of(31, 1, 2015),
                Arguments.of(31, 3, 2015),
                Arguments.of(31, 5, 2015),
                Arguments.of(31, 7, 2015),
                Arguments.of(31, 8, 2015),
                Arguments.of(31, 10, 2015),
                Arguments.of(31, 12, 2015),
                Arguments.of(28,2,2015)
        );
    }

    private static Stream<Arguments> dateAndNextDate_correct() {
        return Stream.of(
                Arguments.of(new Date(18,3,2023), new Date(19,3,2023)),
                Arguments.of(new Date(31,3,2023), new Date(1,4,2023)),
                Arguments.of(new Date(28,2,2023), new Date(1,3,2023)),
                Arguments.of(new Date(30,4,2023), new Date(1,5,2023)),
                Arguments.of(new Date(29,2,2024), new Date(1,3,2024)),
                Arguments.of(new Date(31,12,2023), new Date(1,1,2024)),
                Arguments.of(new Date(31,7,2023), new Date(1,8,2023)),
                Arguments.of(new Date(31,8,2023), new Date(1,9,2023))
        );
    }

    private static Stream<Arguments> dateAndNextDate_incorrect() {
        return Stream.of(
                Arguments.of(new Date(18,3,2023), new Date(20,3,2023)),
                Arguments.of(new Date(31,3,2023), new Date(29,3,2023))
        );
    }

     @Test
    public void testConstructorException(){
       assertThrows(IllegalArgumentException.class, () -> new Date(30,2,2022));
    }

    @ParameterizedTest
    @ValueSource(ints = {2023,1700,1900,-5,-200})
    public void testIsValidDate_notLeapYear(int year){
        assertFalse(Date.isValidDate(29,2,year));
    }

    @ParameterizedTest
    @ValueSource(ints = {2024,1908,1600,0}) //0 n'existe pas dans le calendrier grégorien, mais est bissextile d'après le calendrier julien
    public void testIsValidDate_leapYear(int year){
        assertTrue(Date.isValidDate(29,2,year));
    }

    @ParameterizedTest
    @MethodSource("daysOutOfMonthBound")
    public void testIsValidDate_dayOutOfMonthBound(int day, int month, int year){
        assertFalse(Date.isValidDate(day,month,year));
    }

    @ParameterizedTest
    @MethodSource("daysInMonthBound")
    public void testIsValidDate_dayInMonthBound(int day, int month, int year){
        assertTrue(Date.isValidDate(day,month,year));
    }

    @Test
    public void testIsValidDate_dayOutOfMonthBound_leapYear(){
        assertFalse(Date.isValidDate(30,2,2024));
    }

    @Test
    public void testIsValidDate_dayInMonthBound_leapYear(){
        assertTrue(Date.isValidDate(29,2,2024));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,13,24})
    public void testIsValidDate_monthsOutOfYearBound(int month){
        assertFalse(Date.isValidDate(15,month,2015));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,6,12})
    public void testIsValidDate_monthsInYearBound(int month){
        assertTrue(Date.isValidDate(15,month,2015));
    }

    @Test
    public void testCompareTo(){
        Date d = new Date(18,3,2023);
        assertEquals(0, d.compareTo(new Date(18,3,2023)));
        assertEquals(-1, d.compareTo(new Date(19,3,2023)));
        assertEquals(1, d.compareTo(new Date(17,3,2023)));
    }

    @ParameterizedTest
    @MethodSource("dateAndNextDate_correct")
    public void testNextDate_correct(Date date, Date expected){
        assertEquals(0,expected.compareTo(date.nextDate()));
    }
    @ParameterizedTest
    @MethodSource("dateAndNextDate_incorrect")
    public void testNextDate_incorrect(Date date, Date unexpected){
        assertNotEquals(0,unexpected.compareTo(date.nextDate()));
    }

    @ParameterizedTest
    @MethodSource("dateAndNextDate_correct")
    public void testPreviousDate_correct(Date expected, Date date){
        assertEquals(0,expected.compareTo(date.previousDate()));
    }
    @ParameterizedTest
    @MethodSource("dateAndNextDate_incorrect")
    public void testPreviousDate_incorrect(Date unexpected, Date date){
        assertNotEquals(0, unexpected.compareTo(date.previousDate()));
    }



}