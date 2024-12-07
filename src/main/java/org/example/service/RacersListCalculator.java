package org.example.service;

import org.example.entity.Racer;
import org.example.repository.StatisticReaderIMPL;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RacersListCalculator {
    private final StatisticReaderIMPL statisticReaderIMPL;

    public RacersListCalculator(StatisticReaderIMPL statisticReaderIMPL) {
        this.statisticReaderIMPL = statisticReaderIMPL;
    }

    public List<Racer> getSortedRacersList() {
        return getRacersListWithLapTime().stream()
                .sorted(Comparator.comparing(Racer::getLapTime))
                .toList();
    }

    private List<Racer> getRacersListWithLapTime() {
        var racerList = statisticReaderIMPL.getRacersList();
        var lapTime = getLapTime();
        for (var racer : racerList) {
            if (lapTime.containsKey(racer.getAbbreviation()))
                racer.setLapTime(lapTime.get(racer.getAbbreviation()));
        }
        return racerList;
    }

    private Map<String, String> getLapTime() {
        var startTime = statisticReaderIMPL.getRacerStartTime();
        var endTime = statisticReaderIMPL.getRacerEndTime();
        // var lapTime = new HashMap<String, String>();
        return startTime.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> {
                                    Duration duration = Duration.between(v.getValue(), endTime.get(v.getKey()));
                                    LocalTime baseTime = LocalTime.of(0, 0, 0);
                                    LocalTime resultTime = baseTime.plus(duration);
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m:ss.SSS");
                                    return resultTime.format(formatter);
                                }
                        )
                );
//        endTime.forEach((key, endTimes) -> startTime.merge(key, endTimes, (startTimes, endTimesValue) -> {
//            Duration duration = Duration.between(startTimes, endTimesValue);
//            LocalTime baseTime = LocalTime.of(0, 0, 0);
//            LocalTime resultTime = baseTime.plus(duration);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m:ss.SSS");
//            String formattedTime = resultTime.format(formatter);
//            lapTime.put(key, formattedTime);
//            return startTimes;
//        }));
//        return lapTime;
    }

}
