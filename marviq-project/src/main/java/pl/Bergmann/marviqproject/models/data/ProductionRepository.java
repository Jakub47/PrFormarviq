package pl.Bergmann.marviqproject.models.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.Bergmann.marviqproject.models.production.NetProduction;

import java.util.List;

public interface ProductionRepository extends JpaRepository<ProductionEntity,Integer>
{
    @Query("SELECT new pl.Bergmann.marviqproject.models.production.NetProduction(SUM(value),p.machineName,p.variableName,p.dateTimeFrom,p.dateTimeTo)"
            +" FROM ProductionEntity AS p " +
            " WHERE p.dateTimeFrom BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00' " +
            "  AND  p.variableName  NOT IN  ('CORE TEMPERATURE','SCRAP') " +
            " GROUP by p.machineName")
    List<NetProduction> countNetProductionFromLastDay();

    @Query("SELECT new pl.Bergmann.marviqproject.models.production.NetProduction(SUM(value),p.machineName,p.variableName,p.dateTimeFrom,p.dateTimeTo) "
            +" FROM ProductionEntity AS p " +
            " WHERE p.dateTimeFrom BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00' " +
            "  AND  p.variableName  NOT IN  ('CORE TEMPERATURE','PRODUCTION') " +
            " GROUP by p.machineName")
    List<NetProduction> countNetScrapFromLastDay();

    @Query("SELECT new pl.Bergmann.marviqproject.models.production.NetProduction(SUM(value),p.machineName,p.dateTimeFrom,p.dateTimeTo)"
            + "FROM ProductionEntity AS p WHERE p.dateTimeFrom BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00' AND p.variableName <> 'CORE TEMPERATURE'" +
            " GROUP by p.machineName,p.dateTimeFrom,p.dateTimeTo")
    List<NetProduction> countNetProductionByHours();

    @Query("SELECT p"
            + " FROM ProductionEntity AS p WHERE p.dateTimeFrom BETWEEN '2018-01-07 00:00:00' AND '2018-01-08 00:00:00'" +
            " AND p.variableName = 'CORE TEMPERATURE'")
    List<ProductionEntity> getEntityCoreTemperatures();
}
