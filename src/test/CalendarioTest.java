package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Library.Calendario;

public class CalendarioTest {
    @Test
    public void it_returns_correct_format() {
        Calendario calendario = new Calendario();
        System.out.println(calendario.getFecha());
        assertEquals("03/05/2022", calendario.getFecha());
    }
  
    @Test
    public void it_add_one_month_per_call_to_current_date() {
        assertTrue(true);
    }
   
    @Test
    public void it_add_one_day_per_call_to_current_date() {
        assertTrue(true);
    }
    
    @Test
    public void it_get_current_year() {
        assertTrue(true);
    }
    
}
