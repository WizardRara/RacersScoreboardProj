package org.example.repository;

import org.example.entity.Racer;
import org.example.config.PropertiesLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticReaderIMPL implements RacerStatisticReader {
    private final PropertiesLoader propertiesLoader;
    private static final String START_PATH = "start";
    private static final String END_PATH = "end";
    private static final String ABBREVIATION_PATH ="abbreviations";

    public StatisticReaderIMPL(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    @Override
    public List<Racer> getRacersList() {
        try (var lines = Files.lines(Path.of(propertiesLoader.getProperty(ABBREVIATION_PATH)))) {
            return lines.map(StatisticReaderIMPL::createRacer).toList();
        } catch (IOException x) {
            throw new RuntimeException("File could not be read", x);
        }
    }

    @Override
    public Map<String, LocalTime> getRacerEndTime() {
        return convertStreamToMap(END_PATH);
    }

    @Override
    public Map<String, LocalTime> getRacerStartTime() {
        return convertStreamToMap(START_PATH);
    }

    private Map<String, LocalTime> convertStreamToMap(String path) {
        try (var lines = Files.lines(Path.of(propertiesLoader.getProperty(path)))) {
            return lines.map(s -> s.split("_"))
                    .collect(Collectors.toMap(s -> s[0].substring(0, 3), s -> LocalTime.parse(s[1])));
        } catch (IOException x) {
            throw new RuntimeException("File could not be read", x);
        }
    }

    private static Racer createRacer(String line) {
        String[] splitLine = line.split("_");
        return new Racer(splitLine[0], splitLine[1], splitLine[2]);
    }
}
