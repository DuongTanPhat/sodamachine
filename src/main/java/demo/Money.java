package demo;

public enum Money {
    VND10K(10000),VND20K(20000),VND50K(50000),VND100K(100000),VND200K(200000);
    public final int value;
    private Money(int value){
        this.value=value;
    }
}
