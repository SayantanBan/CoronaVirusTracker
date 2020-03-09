package com.sayantan.coronatracker.service.impl;

import com.sayantan.coronatracker.model.CountryStats;
import com.sayantan.coronatracker.model.LocationStats;
import com.sayantan.coronatracker.model.NumberStats;
import com.sayantan.coronatracker.service.CoronaVirusDataService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoronaVirusDataServiceImpl implements CoronaVirusDataService {

    private static Logger logger = LoggerFactory.getLogger(CoronaVirusDataServiceImpl.class);
    private static String VIRUS_CONFIRMED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private static String VIRUS_DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
    private static String VIRUS_RECOVERED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
    HashMap<String, CountryStats> confirmedCaseCountryMap = new HashMap<>();
    HashMap<String, CountryStats> deathCaseCountryMap = new HashMap<>();
    HashMap<String, CountryStats> recoveredCaseCountryMap = new HashMap<>();
    private List<LocationStats> allConfirmedCaseStats = new ArrayList<>();
    private List<LocationStats> allDeathCaseStats = new ArrayList<>();
    private List<LocationStats> allRecoveredCaseStats = new ArrayList<>();
    private List<CountryStats> confirmedStatus = new ArrayList<>();
    private List<CountryStats> deathStatus = new ArrayList<>();
    private List<CountryStats> recoveredStatus = new ArrayList<>();
    private double confirmedCase = 0, death = 0, recoveredCase = 0;

    @Override
    public List<LocationStats> getAllConfirmedCaseStats() {
        return allConfirmedCaseStats;
    }

    @Override
    public List<LocationStats> getAllDeathCaseStats() {
        return allDeathCaseStats;
    }

    @Override
    public List<LocationStats> getAllRecoveredCaseStats() {
        return allRecoveredCaseStats;
    }

    @Override
    public List<CountryStats> getAllCountryConfirmedStats() {
        confirmedStatus.addAll(confirmedCaseCountryMap.values());
        return confirmedStatus;
    }

    @Override
    public List<CountryStats> getAllCountryDeathStats() {
        deathStatus.addAll(deathCaseCountryMap.values());
        return deathStatus;
    }

    @Override
    public List<CountryStats> getAllCountryRecoverdStats() {
        recoveredStatus.addAll(recoveredCaseCountryMap.values());
        return recoveredStatus;
    }

    @Override
    public double totalConfirmedCase() {
        return confirmedCase;
    }

    @Override
    public double totalDeathToll() {
        return death;
    }

    @Override
    public double totalRecoveredCase() {
        return recoveredCase;
    }

    @Override
    public List<NumberStats> getAllNumbers() {
        logger.debug(">getAllImportantFigures");
        List<NumberStats> stats = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            NumberStats numberStats = new NumberStats();
            if ("0".equals(String.valueOf(i))) {
                numberStats.setTitle("Confirmed Cases");
                numberStats.setNumber(confirmedCase);
            } else if ("1".equals(String.valueOf(i))) {
                numberStats.setTitle("Death Cases");
                numberStats.setNumber(death);
            } else if ("2".equals(String.valueOf(i))) {
                numberStats.setTitle("Recovered Cases");
                numberStats.setNumber(recoveredCase);
            } else if ("3".equals(String.valueOf(i))) {
                numberStats.setTitle("Death Percentage");
                double deathPercentage = death / confirmedCase * 100;
                numberStats.setNumber(Math.round(deathPercentage));
            } else if ("4".equals(String.valueOf(i))) {
                numberStats.setTitle("Recovery Percentage");
                double recoveryPercentage = recoveredCase / confirmedCase * 100;
                numberStats.setNumber(Math.round(recoveryPercentage));
            } else if ("5".equals(String.valueOf(i))) {
                numberStats.setTitle("Treatment Percentage(People undergoing treatment)");
                double recoveryPercentage = ((confirmedCase - (recoveredCase + death)) / confirmedCase) * 100;
                numberStats.setNumber(Math.round(recoveryPercentage));
            }
            stats.add(numberStats);
        }
        logger.debug("<getAllImportantFigures");
        return stats;
    }
    
    private void mapCountryWiseData(HashMap<String, CountryStats> map, CSVRecord record) {
        logger.debug(">mapCountryWiseData");
        if (null != map.get(record.get("Country/Region"))) {
            CountryStats countryStats = map.get(record.get("Country/Region"));
            countryStats.setVirusCase(countryStats.getVirusCase() + Integer.parseInt(record.get(record.size() - 1)));
            HashMap<String, Integer> provinceMap = countryStats.getStateMap();
            provinceMap.put(record.get("Province/State"), Integer.parseInt(record.get(record.size() - 1)));
            countryStats.setStateMap(provinceMap);
            map.put(record.get("Country/Region"), countryStats);
        } else {
            CountryStats countryStats = new CountryStats();
            countryStats.setCountryName(record.get("Country/Region"));
            countryStats.setVirusCase(Integer.parseInt(record.get(record.size() - 1)));
            if (!StringUtils.isEmpty(record.get("Province/State"))) {
                HashMap<String, Integer> provinceMap = new HashMap();
                provinceMap.put(record.get("Province/State"), Integer.parseInt(record.get(record.size() - 1)));
                countryStats.setStateMap(provinceMap);
            } else {
                countryStats.setStateMap(new HashMap<String, Integer>());
            }
            map.put(record.get("Country/Region"), countryStats);
            logger.debug("<mapCountryWiseData");
        }
    }

    public int caseNumber(Iterable<CSVRecord> records, HashMap<String, CountryStats> map, String caseName) {
        logger.debug(">caseNumber");
        int number = 0;
        List<LocationStats> newStats = new ArrayList<>();
        for (CSVRecord record : records) {
            mapCountryWiseData(map, record);
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = 0;
            if (!StringUtils.isEmpty(record.get(record.size() - 1)))
                latestCases = Integer.parseInt(record.get(record.size() - 1));
            number = number + latestCases;
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        if (caseName.equals("confirmed")) {
            this.allConfirmedCaseStats = newStats;
        } else if (caseName.equals("death")) {
            this.allDeathCaseStats = newStats;
        } else if (caseName.equals("recovered")) {
            this.allRecoveredCaseStats = newStats;
        }
        logger.debug("<caseNumber");
        return number;
    }

    @PostConstruct
    @Override
    @Scheduled(cron = "* * * 1 * *")
    public void fetchVirusDataConfirmedCase() throws IOException, InterruptedException {
        logger.debug(">fetchVirusDataConfirmedCase");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_CONFIRMED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        confirmedCase = caseNumber(records, confirmedCaseCountryMap, "confirmed");
        logger.debug("<fetchVirusDataConfirmedCase");
    }

    @PostConstruct
    @Override
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusDataDeath() throws IOException, InterruptedException {
        logger.debug(">fetchVirusDataDeath");
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DEATHS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        death = caseNumber(records, deathCaseCountryMap, "death");
        logger.debug("<fetchVirusDataDeath");
    }

    @PostConstruct
    @Override
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusDataRecovered() throws IOException, InterruptedException {
        logger.debug(">fetchVirusDataRecovered");
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_RECOVERED_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        recoveredCase = caseNumber(records, recoveredCaseCountryMap, "recovered");
        ;
        this.allRecoveredCaseStats = newStats;
        logger.debug("<fetchVirusDataRecovered");
    }
}
