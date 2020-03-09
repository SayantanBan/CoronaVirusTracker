package com.sayantan.coronatracker.service;

import com.sayantan.coronatracker.model.CountryStats;
import com.sayantan.coronatracker.model.LocationStats;
import com.sayantan.coronatracker.model.NumberStats;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CoronaVirusDataService {

    void fetchVirusDataConfirmedCase() throws IOException, InterruptedException;

    void fetchVirusDataDeath() throws IOException, InterruptedException;

    void fetchVirusDataRecovered() throws IOException, InterruptedException;

    List<LocationStats> getAllConfirmedCaseStats();

    List<LocationStats> getAllDeathCaseStats();

    List<LocationStats> getAllRecoveredCaseStats();

    List<NumberStats> getAllNumbers();

    List<CountryStats> getAllCountryConfirmedStats();

    List<CountryStats> getAllCountryDeathStats();

    List<CountryStats> getAllCountryRecoverdStats();

    double totalConfirmedCase();

    double totalDeathToll();

    double totalRecoveredCase();
}
