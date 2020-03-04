package pl.Bergmann.marviqproject.models.common;

import org.springframework.stereotype.Service;
import pl.Bergmann.marviqproject.models.production.PercentageByMachine;
import pl.Bergmann.marviqproject.models.runtime.RuntimeProduction;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculateHelper
{
    public void calculateElementsAndAddValueToList(double ele1, double ele2, List<PercentageByMachine> scrapPerList,
                                                    String machineName)
    {
        double total = ele1 + ele2;
        double totalPercentageOfScarp = (ele2 / total) * 100;

        LocalDateTime dateTimeFrom = LocalDateTime.parse("2018-01-07T00:00:00");
        LocalDateTime dateTimeTo = LocalDateTime.parse("2018-01-08T00:00:00");

        scrapPerList.add(new PercentageByMachine(machineName,dateTimeFrom,dateTimeTo,totalPercentageOfScarp));
    }
}
