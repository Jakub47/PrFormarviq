package pl.Bergmann.marviqproject.models.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "runtime")
@Data
public class RuntimeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="machine_name")
    private String machineName;

    @Column(name="datetime")
    private Date dateTimeOfRunning;

    @Column(name="isrunning")
    private Boolean isRunning;

    public RuntimeEntity(int id, String machineName, Date dateTimeOfRunning, Boolean isRunning) {
        this.id = id;
        this.machineName = machineName;
        this.dateTimeOfRunning = dateTimeOfRunning;
        this.isRunning = isRunning;
    }

    public RuntimeEntity(String machineName, Date dateTimeOfRunning, Boolean isRunning)
    {
        this.machineName = machineName;
        this.dateTimeOfRunning = dateTimeOfRunning;
        this.isRunning = isRunning;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Date getDateTimeOfRunning() {
        return dateTimeOfRunning;
    }

    public void setDateTimeOfRunning(Date dateTimeOfRunning) {
        this.dateTimeOfRunning = dateTimeOfRunning;
    }

    public Boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
