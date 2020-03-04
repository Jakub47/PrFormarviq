package pl.Bergmann.marviqproject.models.data;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "production")
@Data
public class ProductionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="machine_name")
    private String machineName;

    @Column(name="variable_name")
    private String variableName;

    @Column(name="datetime_from")
    private LocalDateTime dateTimeFrom;

    @Column(name="datetime_to")
    private LocalDateTime dateTimeTo;

    private float value;


    public ProductionEntity(String machineName,float value,LocalDateTime dateTimeFrom,LocalDateTime dateTimeTo)
    {
        this.machineName = machineName;
        this.value = value;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
    }

    public ProductionEntity()
    {

    }

    public ProductionEntity(String machineName, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, float value) {
        this.machineName = machineName;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
