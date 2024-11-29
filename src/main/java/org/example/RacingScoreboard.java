package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RacingScoreboard {

    private List<Racer> getSortedRacerList() {
        RacerLapTime racerLapTime = new RacerLapTime();
        return racerLapTime.getFullRacer().stream().sorted(Comparator.comparing(Racer::getLapTime)).toList();
    }

    private int getMaxNameLength(List<Racer> sortedRacerList) {
        var maxNameLength = sortedRacerList.stream()
                .collect(Collectors.summarizingInt(s -> s.getName().length())).getMax();
        int counter = 0;
        for (int i = 0; i < sortedRacerList.size(); i++) {
            int tempLength = sortedRacerList.get(i).getName().length();
            if (tempLength == maxNameLength)
                counter = i;
        }
        if (counter >= 8) {
            maxNameLength = maxNameLength + 1;
        }
        return maxNameLength;
    }

    private int getMaxTeamNameLength(List<Racer> sortedRacerList) {
        return sortedRacerList.stream().collect(Collectors.summarizingInt(s -> s.getRota().length())).getMax();
    }

    private int lapTimeLengthCalculation(List<Racer> sortedRacerList) {
        LocalTime baseTime = LocalTime.of(0, 0, 0);
        LocalTime resultTime = baseTime.plus(sortedRacerList.getFirst().getLapTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m:ss.SSS");
        String formattedTime = resultTime.format(formatter);
        var lapTimeLength = formattedTime.length();
        for (int i = 1; i < lapTimeLength; i++) {
            resultTime = baseTime.plus(sortedRacerList.get(i).getLapTime());
            formattedTime = resultTime.format(formatter);
            int tempLapTimeLength = formattedTime.length();
            if (tempLapTimeLength > lapTimeLength) {
                lapTimeLength = tempLapTimeLength;
            }
        }
        return lapTimeLength;
    }

    public void scoreboardOutput() {
        RacingScoreboard racingScoreboard = new RacingScoreboard();
        var sortedRacerList = racingScoreboard.getSortedRacerList();
        var maxNameLength = getMaxNameLength(sortedRacerList);
        var maxTeamNameLength = getMaxTeamNameLength(sortedRacerList);
        var lapTimeLength = lapTimeLengthCalculation(sortedRacerList);
        for (int i = 0; i < sortedRacerList.size(); i++) {
            Racer racer = sortedRacerList.get(i);
            String formatedLapTime = String.format("%d:%02d.%03d",
                    racer.getLapTime().toMinutesPart(),
                    racer.getLapTime().toSecondsPart(),
                    racer.getLapTime().toMillisPart());
            StringBuilder outputLane = new StringBuilder();
            if (i <= 8) {
                outputLane.append(i + 1).append(".").append(racer.getName())
                        .append(" ".repeat(maxNameLength - racer.getName().length())).append("|")
                        .append(racer.getRota()).append(" ".repeat(maxTeamNameLength - racer.getRota().length()))
                        .append("|").append(formatedLapTime);
                System.out.println(outputLane);
            } else {
                outputLane.append(i + 1).append(".").append(racer.getName())
                        .append(" ".repeat(maxNameLength - 1 - racer.getName().length())).append("|")
                        .append(racer.getRota()).append(" ".repeat(maxTeamNameLength - racer.getRota().length()))
                        .append("|").append(formatedLapTime);
                if (i == 15)
                    System.out.println("-".repeat(5 + (maxNameLength - 1) + maxTeamNameLength + lapTimeLength));
                System.out.println(outputLane);
            }
        }
    }
}
