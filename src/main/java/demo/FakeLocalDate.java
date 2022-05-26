package demo;

import java.time.LocalDate;

public class FakeLocalDate {
    private static LocalDate current = LocalDate.now();

    public static LocalDate getCurrent() {
        return current;
    }

    public static void setCurrent(LocalDate fakeCurrent) {
        if (fakeCurrent == null)
        {
            System.out.println("Error");
            return;
        }
        FakeLocalDate.current = fakeCurrent;
    }
    public static void resetCurrent(){
        FakeLocalDate.current=LocalDate.now();
    }
}
