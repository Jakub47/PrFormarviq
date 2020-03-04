package pl.Bergmann.marviqproject.models.production;

public class HourNetProduction
{
    private int hour;
    private double value;

    public HourNetProduction(int hour, double value)
    {
        this.hour = hour;
        this.value = value;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
