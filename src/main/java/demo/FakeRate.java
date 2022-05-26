package demo;

public class FakeRate {
    private static float rate=0.1f;

    public static float getRate() {
        return rate;
    }

    public static void setRate(float rate) {
        FakeRate.rate = rate;
    }
}
