package pl.Bergmann.marviqproject.models.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.Bergmann.marviqproject.models.runtime.RuntimeMachine;
import pl.Bergmann.marviqproject.models.runtime.RuntimeProduction;

import java.util.List;

public interface RuntimeRepository  extends JpaRepository<RuntimeEntity,Integer>
{
    @Query("SELECT new pl.Bergmann.marviqproject.models.runtime.RuntimeProduction(Count(r.isRunning),r.machineName,r.dateTimeOfRunning,r.isRunning)"
            + "FROM RuntimeEntity AS r WHERE r.dateTimeOfRunning BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00'" +
            " GROUP by r.isRunning,r.machineName")
    List<RuntimeProduction> getTotalRunningStatusFromLastDay();

    @Query("SELECT new pl.Bergmann.marviqproject.models.runtime.RuntimeMachine(Count(r.isRunning),r.machineName)"
            + "FROM RuntimeEntity AS r WHERE r.isRunning = 1 AND " +
            " r.dateTimeOfRunning BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00'" +
            " GROUP by r.machineName")
    List<RuntimeMachine> groupedRunningTimesOfMachines();
}
