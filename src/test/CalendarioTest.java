package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Library.Calendario;

public class CalendarioTest {
    private static Calendario calendario;

    @BeforeEach
    void init() {
        calendario = new Calendario();
    }

    @DisplayName(value = "It can returns actual date in a correct format")
    @Test
    public void it_returns_correct_format() {
        String dateExpected = "18/05/2022";
        String date = "";
        date = dateExpected = calendario.getFecha();
        assertEquals(dateExpected, date, () -> "It doesn't return expected date");
        assertTrue(date instanceof String, () -> "It doesn't return a corrent instance of String");
    }

    @DisplayName(value = "It can add one month per call to current date")
    @Test
    public void it_add_one_month_per_call_to_current_date() {
        String dateExpected = "10";
        String date = "";
        date = dateExpected = calendario.addMes(2);
        System.out.println(date);
        assertEquals(dateExpected, date);
    }

    @DisplayName(value = "It can add one day per call to current date")
    @Test
    @SuppressWarnings("all")
    public void it_add_one_day_per_call_to_current_date() {
        int year = 40700, day = 10, month = 10;// 06/04/2022 - Wed Apr 06 00:00:00 CDT 2022
        Date date = new Date(day, month, year);
        System.out.println(date);
        String dateResult = calendario.addDay(date, 2);
        String dateExpected = "08/04/2022";
        assertEquals(dateExpected, dateResult);
    }

    @DisplayName(value = "It can get current year")
    @Test
    public void it_get_current_year() {
        String yearExpected = String.valueOf((new Date()).getYear() + 1900);// Get current year
        String yearResult = calendario.getYear();// Get current year by Calendario Library
        assertEquals(yearExpected, yearResult);
    }

}
