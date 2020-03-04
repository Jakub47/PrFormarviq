package pl.Bergmann.marviqproject.models.runtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.Bergmann.marviqproject.models.common.CalculateHelper;
import pl.Bergmann.marviqproject.models.data.RuntimeRepository;
import pl.Bergmann.marviqproject.models.production.PercentageByMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RunTimeReporter
{
    private RuntimeRepository runtimeRepository;
    private CalculateHelper calculateHelper;
    private List<RuntimeProduction> totalRunningStatusFromLastDay;
    private List<RuntimeMachine> runningMachinesTime;

    @Autowired
    public RunTimeReporter(RuntimeRepository runtimeRepository, CalculateHelper calculateHelper)
    {
        this.runtimeRepository = runtimeRepository;
        this.calculateHelper = calculateHelper;
        this.totalRunningStatusFromLastDay = runtimeRepository.getTotalRunningStatusFromLastDay();
        this.runningMachinesTime = runtimeRepository.groupedRunningTimesOfMachines();
    }

    public RunTimeReporter() {
    }

    /**
     * The percentage of downtime for a machine
     * @return List of downtime for machines
     */
    public List<PercentageByMachine> getRunTimeStats()
    {
        Map<String, List<RuntimeProduction>> pr =
                totalRunningStatusFromLastDay.stream().collect(Collectors.groupingBy(w -> w.getMachineName()));

        List<PercentageByMachine> percentageByMachines = new ArrayList<PercentageByMachine>();

        pr.forEach( (k,v) -> CalculateRunningAmountsAndInitialize(percentageByMachines,v,k));

        return percentageByMachines;
    }

    /**
     * Calculates the actual downtime
     * @param scrapPerList  Empty list in which actuall percentages will be added
     * @param netProdList List containing machines with values
     * @param machineName Current machine
     */
    private void CalculateRunningAmountsAndInitialize(List<PercentageByMachine> scrapPerList, List<RuntimeProduction> netProdList, String machineName)
    {
        double runningAmount = netProdList.stream().filter(a -> a.getRunning() == true).findFirst().get()
                .getAmountOfRunning();
        double notRunningAmount = netProdList.stream().filter(a -> a.getRunning() == false).findFirst().get()
                .getAmountOfRunning();
        calculateHelper.calculateElementsAndAddValueToList(runningAmount,notRunningAmount,scrapPerList,machineName);
    }


    public List<RuntimeMachine> getRunningMachinesTime() {
        return runningMachinesTime;
    }
}
