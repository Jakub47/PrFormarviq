package pl.Bergmann.marviqproject.models.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.Bergmann.marviqproject.controllers.HomeController;
import pl.Bergmann.marviqproject.models.common.CalculateHelper;
import pl.Bergmann.marviqproject.models.common.StatusOfMachine;
import pl.Bergmann.marviqproject.models.data.ProductionEntity;
import pl.Bergmann.marviqproject.models.data.ProductionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductionReporter
{
    private ProductionRepository productionRepo;
    private List<NetProduction> production;
    private List<NetProduction> scrap;
    private List<NetProduction> allItems;
    private List<NetProduction> countNetProductionByHours;
    private CalculateHelper calculateHelper;
    List<ProductionEntity> entityCoreTemperatures;

    @Autowired
    public ProductionReporter(ProductionRepository productionRepo, CalculateHelper calculateHelper)
    {
        this.productionRepo = productionRepo;
        production = productionRepo.countNetProductionFromLastDay();
        scrap = productionRepo.countNetScrapFromLastDay();
        countNetProductionByHours = productionRepo.countNetProductionByHours();
        this.entityCoreTemperatures = productionRepo.getEntityCoreTemperatures();
        this.calculateHelper = calculateHelper;
        allItems = Stream.concat(production.stream(), scrap.stream()).collect(Collectors.toList());
    }

    /**
     * The total net production for the machine (gross output minus scrap)
     * @return List for each machine contains total net production
     */
    public List<NetProduction> countNetProduction()
    {
        List<NetProduction> netProductionToReturn = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        for (int i = 0;i<production.size();i++)
        {
            NetProduction currentProduction = production.get(i);
            NetProduction netProduction = scrap.stream().filter(a -> a.getMachineName().equals(currentProduction.getMachineName()))
                    .findFirst().get();

            double diffrence = currentProduction.getProduction() - netProduction.getProduction();

            NetProduction machineNetProduction = new NetProduction(diffrence,netProduction.getMachineName(),
                    LocalDateTime.parse(netProduction.getDateTimeFrom().toString().replace('T',' '), formatter),
                    LocalDateTime.parse("2018-01-08 00:00",formatter)
            );
            netProductionToReturn.add(machineNetProduction);
        }
        return netProductionToReturn;
    }

    /**
     * The percentage of scrap vs gross production
     * @return List for each machine contains percentage of scrap
     */
    public List<PercentageByMachine> getScrapPercentages()
    {
        Map<String, List<NetProduction>> pr = mapNetProductionByMachineName(allItems);
        List<PercentageByMachine> percentageByMachines = new ArrayList<PercentageByMachine>();

        pr.forEach( (k,v) -> CalculatePercentageAndInitialize(percentageByMachines,v,k));

        return percentageByMachines;
    }

    /**
     * Net production  for every hour
     * @return list containing  Net production  for every hour of machines
     */
    public List<NetProductionHour> retrieveNetProductionByMachineName()
    {
        Map<String, List<NetProduction>> pr = mapNetProductionByMachineName(countNetProductionByHours);

        List<NetProductionHour> netProductionsToShow = new ArrayList<>();

        pr.forEach((k,v) -> initializeListOfDowntime(v,k,netProductionsToShow));
        return netProductionsToShow;
    }

    /**
     *  Returns status of the machine based on it’s core temperature over de last 24 hours.
     * @return Map containg machines and their statutes
     */
    public Map<String,StatusOfMachine>  getTemperatureStatusFromMachine()
    {
        Map<String, List<ProductionEntity>> pr =
                entityCoreTemperatures.stream().collect(Collectors.groupingBy(w -> w.getMachineName()));

        //Here will be put machine with a colour status
        Map<String,StatusOfMachine> machineStatus =  new HashMap<>();

        pr.forEach( (k,v) -> checkForOverHeatingInMachine(machineStatus,k,v));

        return machineStatus;
    }

    /**
     * Groups machines by their name
     * @param param List containing machines
     * @return Grouped machines
     */
    private Map<String, List<NetProduction>> mapNetProductionByMachineName(List<NetProduction> param)
    {
        return  param.stream().collect(Collectors.groupingBy(w -> w.getMachineName()));
    }

    /**
     * Calculates Percentages in each machine in last 24 hours
     * @param scrapPerList Empty list in which actuall percentages will be added
     * @param netProdList List containing machines with values
     * @param machineName Current machine
     */
    private void CalculatePercentageAndInitialize(List<PercentageByMachine> scrapPerList, List<NetProduction> netProdList, String machineName)
    {
        double prodPercentage = netProdList.stream().filter(a -> a.getVariableName().equals("PRODUCTION")).findFirst().get()
                .getProduction();
        double scarpPercentage = netProdList.stream().filter(a -> a.getVariableName().equals("SCRAP")).findFirst().get()
                .getProduction();

        calculateElementsAndAddValueToList(prodPercentage,scarpPercentage,scrapPerList,machineName);
    }

    /**
     * Method for counting Total and creating new object with calculated total
     * @param ele1 first element for counting
     * @param ele2 second element for counting
     * @param scrapPerList list in which actuall percentages will be added
     * @param machineName Current machine
     */
    private void calculateElementsAndAddValueToList(double ele1, double ele2, List<PercentageByMachine> scrapPerList,
                                                    String machineName)
    {
        double total = ele1 + ele2;
        double totalPercentageOfScarp = (ele2 / total) * 100;

        LocalDateTime dateTimeFrom = LocalDateTime.parse("2018-01-07T00:00:00");
        LocalDateTime dateTimeTo = LocalDateTime.parse("2018-01-08T00:00:00");

        scrapPerList.add(new PercentageByMachine(machineName,dateTimeFrom,dateTimeTo,totalPercentageOfScarp));
    }

    /**
     * Counts net production for every hour in provided machine
     * @param v List with all hours and values
     * @param k Current machine
     * @param listOfNetToShow List which will contain net production for every hour
     */
    private  void initializeListOfDowntime(List<NetProduction> v, String k,List<NetProductionHour> listOfNetToShow)
    {
        int countMinutesForHour = 0;
        int counterOfHours = 0;
        float counterForNetProduction = 0;

        NetProductionHour netProductionToReturn = new NetProductionHour(k,
                LocalDateTime.of(2018,01,7,0,0,0),
                LocalDateTime.of(2018,01,8,0,0,0));

        for (NetProduction netProduction : v)
        {
            int diffrence = netProduction.getDateTimeTo()
                    .minusMinutes(netProduction.getDateTimeFrom().getMinute()).getMinute();

            countMinutesForHour += diffrence;
            counterForNetProduction += netProduction.getProduction();

            if(countMinutesForHour >= 60)
            {

                HourNetProduction hP = new HourNetProduction(counterOfHours,counterForNetProduction);
                netProductionToReturn.addHourNetProduction(hP);
                counterOfHours+=1;
                counterForNetProduction = 0;
                countMinutesForHour =0;
            }
        }

        listOfNetToShow.add(netProductionToReturn);
    }

    /**
     * Checks if machine overheat
     * @param machineStatus Map which will have status of machine
     * @param machine Current machine
     * @param data Data to check for overheat
     */
    private void checkForOverHeatingInMachine(Map<String,StatusOfMachine> machineStatus, String machine,
                                              List<ProductionEntity> data)
    {
        int counterWarining = 0;
        boolean overOneHundred = false;

        final int durationChecking = 15;
        int currentTime = 0;
        float currentValue = 0;

        for (ProductionEntity nP : data)
        {
            currentValue = nP.getValue();

            if(currentValue > 85 && currentValue < 100)
                counterWarining++;
            else if (currentValue >= 100)
                overOneHundred = true;
            else
            {
                counterWarining = 0;
                overOneHundred = false;
            }

            //Here function will not end since it still needs to check wheter fatal occur
            if(counterWarining >= 3)
                machineStatus.put(machine,StatusOfMachine.Warning);

            if(overOneHundred || counterWarining >= 4) {
                machineStatus.put(machine, StatusOfMachine.Fatal);
                return;
            }
        }

        if(! (machineStatus.containsKey(machine)))
            machineStatus.put(machine, StatusOfMachine.Good);
    }


    public List<NetProduction> getAllItems() {
        return allItems;
    }


    public List<NetProduction> getScrap() {
        return scrap;
    }

    public List<NetProduction> getProduction() {
        return production;
    }
}
