package org.example.view;

import org.example.entity.Racer;
import org.example.service.RacersListCalculator;

import java.util.List;

public class RacingScoreboard {
    private static final Integer EXTRA_SIZE_LENGTH = 13;
    private static final int TOP_RACERS = 15;

    private final RacersListCalculator racersListCalculator;

    public RacingScoreboard(RacersListCalculator racersListCalculator) {
        this.racersListCalculator = racersListCalculator;
    }

    public String scoreboardOutput() {
        var sortedRacersList = racersListCalculator.getSortedRacersList();
        var maxNameLength = getMaxNameLength(sortedRacersList);
        var maxTeamNameLength = getMaxTeamNameLength(sortedRacersList);
        var counter = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (var racer : sortedRacersList) {
            String counterString = String.format(
                    "%2d.%-" + maxNameLength + "s|%-" + maxTeamNameLength + "s|%s%s",
                    counter++, racer.getName(), racer.getRota(), racer.getLapTime(), System.lineSeparator()
            );
            stringBuilder.append(counterString);
            if (counter == TOP_RACERS + 1)
                stringBuilder.append("-".repeat(EXTRA_SIZE_LENGTH + maxNameLength + maxTeamNameLength))
                        .append(System.lineSeparator());
        }
        return  stringBuilder.toString();
    }

    private int getMaxNameLength(List<Racer> sortedRacersList) {
        return sortedRacersList.stream().mapToInt(racer -> racer.getName().length()).max().orElse(0);
    }

    private int getMaxTeamNameLength(List<Racer> sortedRacersList) {
        return sortedRacersList.stream().map(racer -> racer.getRota().length()).max(Integer::compareTo).orElse(0);
    }
}
