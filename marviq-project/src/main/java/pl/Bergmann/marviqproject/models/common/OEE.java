package pl.Bergmann.marviqproject.models.common;

import pl.Bergmann.marviqproject.models.production.NetProduction;

import java.time.LocalDateTime;

public class OEE extends NetProduction
{
    private double performance;
    private double avaibility;
    private double quality;
    private double oeeOveroal;

    public double getPerformance() {
        return performance / 100;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public double getAvaibility() {
        return avaibility / 100;
    }

    public void setAvaibility(double avaibility) {
        this.avaibility = avaibility;
    }

    public double getQuality() { return quality / 100; }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getOeeOveroal() { return oeeOveroal; }

    public void setOeeOveroal(double oeeOveroal) {
        this.oeeOveroal = oeeOveroal;
    }

    public OEE(double production, String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo)
    {
        super(production, machineName, dateTimeFrom, dateTimeTo);
    }

}
