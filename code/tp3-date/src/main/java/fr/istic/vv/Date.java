package fr.istic.vv;

import java.util.Objects;

class Date implements Comparable<Date> {

    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        if(!isValidDate(day,month,year)){
            throw new IllegalArgumentException("Invalid date");
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if(day<1 || day>31){
            return false;
        }
        if(month<1 || month>12){
            return false;
        }

        if(month == 2){
            if (isLeapYear(year)) {
                return day <= 29;
            }else{
                return day <= 28;
            }
        }

        if(month == 4 || month == 6 || month == 9 || month == 11){
            return day <= 30;
        }

        return true;
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
    }

    public Date nextDate() {
        if(isValidDate(day+1,month,year)){
            return new Date(day+1,month,year);
        }
        if(isValidDate(1,month+1,year)){
            return new Date(1,month+1,year);
        }
        //if(isValidDate(1,1,year+1)){
            return new Date(1,1,year+1);
        //}
        //return null;
    }

    public Date previousDate() {
        if(isValidDate(day-1,month,year)){
            return new Date(day-1,month,year);
        }
        if(isValidDate(1,month-1,year)){
            if(isValidDate(31,month-1,year)){
                return new Date(31,month-1,year);
            } else if (isValidDate(30,month-1,year)) {
                return new Date(30,month-1,year);
            } else if (isValidDate(29,month-1,year)) {
                return new Date(29,month-1,year);
            }
            return new Date(28,month-1,year);
        }
        //if(isValidDate(1,1,year-1)){
        return new Date(31,12,year-1);
        //}
        //return null;
    }

    public int compareTo(Date other) {
        Objects.requireNonNull(other);
        return (year*10000 + month*100 + day) - (other.year*10000 + other.month*100 + other.day);
    }

}