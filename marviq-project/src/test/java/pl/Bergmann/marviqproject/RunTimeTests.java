package pl.Bergmann.marviqproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.Bergmann.marviqproject.models.data.RuntimeRepository;
import pl.Bergmann.marviqproject.models.production.PercentageByMachine;
import pl.Bergmann.marviqproject.models.runtime.RunTimeReporter;
import pl.Bergmann.marviqproject.models.runtime.RuntimeProduction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class RunTimeTests
{

	@Mock
	RuntimeRepository repo;

	@InjectMocks
	RunTimeReporter runTimeReporter;

	@Before
	public void init() throws ParseException
	{
		given(repo.getTotalRunningStatusFromLastDay()).willReturn(prepareDate());
	}

	@Test
	void isRightAmountOfMachines()
	{
		int amountShouldBe = 3;//since 2 machines are in list
		List<PercentageByMachine> runTimeStats = runTimeReporter.getRunTimeStats();

		Assert.assertTrue(amountShouldBe == runTimeStats.size());

	}

	private List<RuntimeProduction> prepareDate() throws ParseException
	{
		List<RuntimeProduction> rP = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		rP.add(new RuntimeProduction(100L,"first machine",
				df.parse("10/10/2015"),true));
		rP.add(new RuntimeProduction(55L,"first machine",
				df.parse("10/10/2015"),false));
		rP.add(new RuntimeProduction(43L,"second machine",
				df.parse("10/10/2015"),true));
		rP.add(new RuntimeProduction(77L,"second machine",
				df.parse("10/10/2015"),false));
		rP.add(new RuntimeProduction(43L,"third machine",
				df.parse("10/10/2015"),true));
		rP.add(new RuntimeProduction(77L,"third machine",
				df.parse("10/10/2015"),false));
		return rP;
	}
}
