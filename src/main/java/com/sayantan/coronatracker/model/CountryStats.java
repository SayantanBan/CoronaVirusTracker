package com.sayantan.coronatracker.model;

import java.util.HashMap;

public class CountryStats implements Comparable<CountryStats> {
    private String countryName;
    private int virusCase;
    private HashMap<String, Integer> stateMap;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getVirusCase() {
        return virusCase;
    }

    public void setVirusCase(int virusCase) {
        this.virusCase = virusCase;
    }

    public HashMap<String, Integer> getStateMap() {
        return stateMap;
    }

    public void setStateMap(HashMap<String, Integer> stateMap) {
        this.stateMap = stateMap;
    }

    @Override
    public String toString() {
        return "CountryStats{" +
                "countryName='" + countryName + '\'' +
                ", virusCase=" + virusCase +
                ", stateMap=" + stateMap +
                '}';
    }

    @Override
    public int compareTo(CountryStats o) {
        if(this.getVirusCase()>o.getVirusCase())
            return 1;
        else if(this.getVirusCase()<o.getVirusCase()){
            return -1;
        }
        return 0;
    }
}
