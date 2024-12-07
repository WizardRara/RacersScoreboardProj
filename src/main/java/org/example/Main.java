package org.example;

import org.example.config.PropertiesLoader;
import org.example.repository.StatisticReaderIMPL;
import org.example.service.RacersListCalculator;
import org.example.view.RacingScoreboard;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        RacingScoreboard racingScoreboard = new RacingScoreboard(new RacersListCalculator
                (new StatisticReaderIMPL(new PropertiesLoader(new Properties()))));
        System.out.println(racingScoreboard.scoreboardOutput());
        //ne sosay
    }
}
