package pl.Bergmann.marviqproject.models.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.Bergmann.marviqproject.models.production.NetProduction;
import pl.Bergmann.marviqproject.models.production.ProductionReporter;
import pl.Bergmann.marviqproject.models.runtime.RunTimeReporter;
import pl.Bergmann.marviqproject.models.runtime.RuntimeMachine;

import java.util.ArrayList;
import java.util.List;

@Service
public class OEEReporter
{

    private ProductionReporter productionReporter;
    private RunTimeReporter runTimeReporter;

    @Autowired
    public OEEReporter(ProductionReporter productionReporter, RunTimeReporter runTimeReporter)
    {
        this.productionReporter = productionReporter;
        this.runTimeReporter = runTimeReporter;
    }

    public List<OEE> calculOEEStatus()
    {
        List<OEE> oeeStatus =  new ArrayList<>();
        initailizeOeeObjectAndCalculatePerformance(oeeStatus);
        calculateAvaibilityOfMachines(oeeStatus);
        calculateQuality(oeeStatus);

        oeeStatus.forEach(a -> a.setOeeOveroal
        (
            (a.getAvaibility() * a.getPerformance() * a.getQuality())
        ));

        return oeeStatus;
    }



    private void initailizeOeeObjectAndCalculatePerformance(List<OEE> oeeStatus )
    {
        List<NetProduction> production =  this.productionReporter.getProduction();
        for (NetProduction nP : production)
        {
            OEE oee = new OEE(nP.getProduction(), nP.getMachineName(), nP.getDateTimeFrom(), nP.getDateTimeTo());
            float normGross = 30000 * 24;
            oee.setPerformance(oee.getProduction() / normGross * 100);
            oeeStatus.add(oee);
        }
    }

    private void calculateAvaibilityOfMachines(List<OEE> oeeStatus)
    {
        List<RuntimeMachine> runningMachinesTime = runTimeReporter.getRunningMachinesTime();

        for (RuntimeMachine rM : runningMachinesTime)
        {
            //get running Times of Machine
            double runningTimes = rM.getCountRunning();
            runningTimes*=5; //since each running takes 5 minutes [00:00:00 - 00:05:00] convert running to minutes
            runningTimes/=60; //convert minutes to hours
            double normUptime = 16; // 75% of up time

            double result = runningTimes / 16 * 100;

            oeeStatus.stream().filter(a -> a.getMachineName().equals(rM.getMachineName())).findFirst().get()
                    .setAvaibility(result);
        }
    }

    private void calculateQuality(List<OEE> oeeStatus)
    {
        List<NetProduction> production =  productionReporter.getProduction();
        List<NetProduction> scrap =   productionReporter.getScrap();

        for (NetProduction nP : production)
        {
            NetProduction currentScrapOfMachine = scrap.stream().filter(a -> a.getMachineName().equals(nP.getMachineName())).findFirst().get();
            double result = ( nP.getProduction() - currentScrapOfMachine.getProduction() ) / nP.getProduction() * 100;

            oeeStatus.stream().filter(a -> a.getMachineName().equals(nP.getMachineName())).findFirst().get().setQuality(result);
        }
    }
}
