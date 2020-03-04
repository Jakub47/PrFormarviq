package pl.Bergmann.marviqproject.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.Bergmann.marviqproject.models.common.OEE;
import pl.Bergmann.marviqproject.models.common.OEEReporter;
import pl.Bergmann.marviqproject.models.common.StatusOfMachine;
import pl.Bergmann.marviqproject.models.production.NetProduction;
import pl.Bergmann.marviqproject.models.production.NetProductionHour;
import pl.Bergmann.marviqproject.models.production.PercentageByMachine;
import pl.Bergmann.marviqproject.models.production.ProductionReporter;
import pl.Bergmann.marviqproject.models.runtime.RunTimeReporter;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController
{
    private RunTimeReporter runTimeReporter;
    private ProductionReporter productionReporter;
    private OEEReporter oeeReporter;

    public HomeController(RunTimeReporter runTimeReporter, ProductionReporter productionReporter, OEEReporter oeeReporter) {
        this.runTimeReporter = runTimeReporter;
        this.productionReporter = productionReporter;
        this.oeeReporter = oeeReporter;
    }


    @GetMapping("/A1")
    public List<NetProduction> A1()
    {
        List<NetProduction> taskA1List = productionReporter.countNetProduction();
        return taskA1List;
    }

    @GetMapping("/A2")
    public List<PercentageByMachine> A2()
    {
        List<PercentageByMachine> taskA2List = productionReporter.getScrapPercentages();
        return taskA2List;
    }

    @GetMapping("/A3")
    public List<PercentageByMachine> A3()
    {
        List<PercentageByMachine> taskA3List = runTimeReporter.getRunTimeStats();
        return taskA3List;
    }

    @GetMapping("/A4")
    public List<NetProductionHour> A4()
    {
        List<NetProductionHour> taskA4List = productionReporter.retrieveNetProductionByMachineName();
        return taskA4List;
    }


    @GetMapping("/B")
    public Map<String, StatusOfMachine> B()
    {
        Map<String, StatusOfMachine> taskBMap = productionReporter.getTemperatureStatusFromMachine();
        return taskBMap;
    }


    @GetMapping("/C")
    public List<OEE> C()
    {
        List<OEE> taskBist = oeeReporter.calculOEEStatus();
        return taskBist;
    }
}
