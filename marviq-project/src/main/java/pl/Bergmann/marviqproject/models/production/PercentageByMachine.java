package pl.Bergmann.marviqproject.models.production;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PercentageByMachine
{
    private String machine;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private double percentage;

    public PercentageByMachine(String machine, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, double percentage) {
        this.machine = machine;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.percentage = percentage;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public double getPercentage() {
        return percentage / 100;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
