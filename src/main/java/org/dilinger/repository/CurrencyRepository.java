package org.dilinger.repository;

import org.dilinger.entity.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

    @Override
    public <S extends Currency> S save(S entity);

    @Query(value = "SELECT * FROM currency WHERE date=?1", nativeQuery = true)
    List<Currency> findByDate(Date date);

    @Query(value = "SELECT * FROM currency WHERE date BETWEEN DATE(?1) AND DATE(?2)", nativeQuery = true)
    List<Currency> findByPeriod(String startDate, String endDate);


}
