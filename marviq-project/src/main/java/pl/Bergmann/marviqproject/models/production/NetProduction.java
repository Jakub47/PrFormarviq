package pl.Bergmann.marviqproject.models.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NetProduction
{
    private String machineName;
    private double production;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private String variableName;




    public NetProduction(String machineName,LocalDateTime dateTimeFrom,LocalDateTime dateTimeTo)
    {
        this.machineName = machineName;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }

    public NetProduction(String machineName, double production,String variableName)
    {
        this.machineName = machineName;
        this.production = production;
        this.variableName = variableName;
    }

    public NetProduction(double production,String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        this.machineName = machineName;
        this.production = production;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }

    public NetProduction(double production,String machineName,String variableName,LocalDateTime dateTimeFrom,
                         LocalDateTime dateTimeTo)
    {
        this.machineName = machineName;
        this.production = production;
        this.variableName = variableName;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }


    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
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



    @JsonIgnore
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public static Predicate<NetProduction> isAdultMale(String variableName)
    {
        return a -> a.getVariableName().equals(variableName);
    }



}
