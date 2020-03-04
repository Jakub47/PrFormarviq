package pl.Bergmann.marviqproject.models.production;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NetProductionHour extends NetProduction
{
    private List<HourNetProduction> hourNetProductions = new ArrayList<>();

    public NetProductionHour(String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        super(machineName, dateTimeFrom, dateTimeTo);
    }

    public NetProductionHour(String machineName, double production, String variableName) {
        super(machineName, production, variableName);
    }

    public NetProductionHour(double production, String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        super(production, machineName, dateTimeFrom, dateTimeTo);
    }

    public NetProductionHour(double production, String machineName, String variableName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        super(production, machineName, variableName, dateTimeFrom, dateTimeTo);
    }

    public void addHourNetProduction(HourNetProduction hP)
    {
        hourNetProductions.add(hP);
    }

    public List<HourNetProduction> getHourNetProductions() {
        return hourNetProductions;
    }
}
