package com.sayantan.coronatracker.resource;

import com.sayantan.coronatracker.model.CountryStats;
import com.sayantan.coronatracker.model.LocationStats;
import com.sayantan.coronatracker.model.NumberStats;
import com.sayantan.coronatracker.service.CoronaVirusDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VirusDataController {

    private static Logger logger = LoggerFactory.getLogger(VirusDataController.class);

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/fetchAllImportantFigures")
    public List<NumberStats> fetchAllNumbers() {
        logger.info(">fetchConfirmedCaseDetails");
        logger.info("<fetchConfirmedCaseDetails");
        return coronaVirusDataService.getAllNumbers();
    }

    @GetMapping("/fetchConfirmedCaseDetails/page")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<LocationStats> fetchConfirmedCaseDetails(@RequestParam("page") int page) {
        logger.info(">fetchConfirmedCaseDetails");
        logger.info("<fetchConfirmedCaseDetails");
        return coronaVirusDataService.getAllConfirmedCaseStats(page);
    }

    @GetMapping("/fetchConfirmedCaseDetails")
    public List<LocationStats> fetchConfirmedCaseDetails() {
        logger.info(">fetchConfirmedCaseDetails");
        logger.info("<fetchConfirmedCaseDetails");
        return coronaVirusDataService.getAllConfirmedCaseStats();
    }

    @GetMapping("/fetchConfirmedCaseNumber")
    public double fetchConfirmedCase() {
        logger.info(">fetchConfirmedCase");
        logger.info("<fetchConfirmedCase");
        return coronaVirusDataService.totalConfirmedCase();
    }

    @GetMapping("/fetchDeathCaseDetails/page")
    public List<LocationStats> fetchDeathCaseDetails(@RequestParam("page") int page) {
        logger.info(">fetchDeathCaseDetails");
        logger.info("<fetchDeathCaseDetails");
        return coronaVirusDataService.getAllDeathCaseStats(page);
    }

    @GetMapping("/fetchDeathCaseDetails")
    public List<LocationStats> fetchDeathCaseDetails() {
        logger.info(">fetchDeathCaseDetails");
        logger.info("<fetchDeathCaseDetails");
        return coronaVirusDataService.getAllDeathCaseStats();
    }

    @GetMapping("/fetchDeathCaseNumber")
    public double fetchDeathCase() {
        logger.info(">fetchDeathCase");
        logger.info("<fetchDeathCase");
        return coronaVirusDataService.totalDeathToll();
    }

    @GetMapping("/fetchRecoveredCaseDetails/page")
    public List<LocationStats> fetchRecoveredCaseDetails(@RequestParam("page") int page) {
        logger.info(">fetchRecoveredCaseDetails");
        logger.info("<fetchRecoveredCaseDetails");
        return coronaVirusDataService.getAllRecoveredCaseStats(page);
    }

    @GetMapping("/fetchRecoveredCaseDetails")
    public List<LocationStats> fetchRecoveredCaseDetails() {
        logger.info(">fetchRecoveredCaseDetails");
        logger.info("<fetchRecoveredCaseDetails");
        return coronaVirusDataService.getAllRecoveredCaseStats();
    }

    @GetMapping("/fetchRecoveredCaseNumber")
    public double fetchRecoveredCase() {
        logger.info(">fetchRecoveredCase");
        logger.info("<fetchRecoveredCase");
        return coronaVirusDataService.totalRecoveredCase();
    }

    @GetMapping("/fetchCountryWiseConfirmedCase")
    public List<CountryStats> fetchCountryWiseConfirmedCase() {
        return coronaVirusDataService.getAllCountryConfirmedStats();
    }

    @GetMapping("/fetchCountryWiseDeathCase")
    public List<CountryStats> fetchCountryWiseDeathCase() {
        return coronaVirusDataService.getAllCountryDeathStats();
    }

    @GetMapping("/fetchCountryRecoveredCase")
    public List<CountryStats> fetchCountryRecoveredCase() {
        return coronaVirusDataService.getAllCountryRecoverdStats();
    }
}
