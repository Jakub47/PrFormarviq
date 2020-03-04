package pl.Bergmann.marviqproject.models.runtime;

import pl.Bergmann.marviqproject.models.data.RuntimeEntity;

import java.util.Date;

public class RuntimeProduction extends RuntimeEntity
{
    private Long amountOfRunning;

    public RuntimeProduction(Long amountOfRunning,String machineName, Date dateTimeOfRunning, Boolean isRunning)
    {
        super(machineName, dateTimeOfRunning, isRunning);
        this.amountOfRunning = amountOfRunning;
    }

    public Long getAmountOfRunning() {
        return amountOfRunning;
    }

    public void setAmountOfRunning(Long amountOfRunning) {
        this.amountOfRunning = amountOfRunning;
    }
}
