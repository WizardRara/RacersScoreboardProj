package org.example;

import java.util.List;

public class RacerLapTime {
    public List<Racer> getFullRacer() {
        AbbreviationsExtractor abbreviationsExtractor = new AbbreviationsExtractor();
        var racersList = abbreviationsExtractor.getAbbreviationRacerList();
        LapTimeCalculator lapTimeCalculator = new LapTimeCalculator();
        var lapTime = lapTimeCalculator.getLapTime();
        for (var racer : racersList) {
            if (lapTime.containsKey(racer.getAbbreviation()))
                racer.setLapTime(lapTime.get(racer.getAbbreviation()));
        }
        return racersList;
    }
}
