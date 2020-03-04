package pl.Bergmann.marviqproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.Bergmann.marviqproject.models.data.ProductionRepository;
import pl.Bergmann.marviqproject.models.data.RuntimeRepository;
import pl.Bergmann.marviqproject.models.production.NetProduction;
import pl.Bergmann.marviqproject.models.production.PercentageByMachine;
import pl.Bergmann.marviqproject.models.production.ProductionReporter;
import pl.Bergmann.marviqproject.models.runtime.RunTimeReporter;
import pl.Bergmann.marviqproject.models.runtime.RuntimeProduction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class NetProductionTests
{
    @Mock
    ProductionRepository pR;

    @InjectMocks
    ProductionReporter nP;

    @Before
    public void init() throws ParseException
    {
        given(pR.countNetProductionFromLastDay()).willReturn(prepareDate());
    }

    @Test
    void isRightAmountOfMachines()
    {
        int amountShouldBe = 500;//since 700-200
        List<NetProduction> netProductions = nP.countNetProduction();

        Assert.assertTrue(amountShouldBe == netProductions.get(0).getProduction());

    }

    private List<NetProduction> prepareDate() throws ParseException
    {
        List<NetProduction> rP = new ArrayList<>();

        rP.add(new NetProduction(700D,"first machine",
            "PRODUCTION",LocalDateTime.parse("2015-02-20T00:00:00"),
            LocalDateTime.parse("2015-02-21T00:00:00")));
        rP.add(new NetProduction(200D,"second machine",
                "SCRAP",LocalDateTime.parse("2015-02-20T00:00:00"),
                LocalDateTime.parse("2015-02-21T00:00:00")));
        return rP;
    }
}
