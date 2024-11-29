package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AbbreviationsExtractor {

    private static Racer createRacer(String line) {
        String[] splitLine = line.split("_");
        return new Racer(splitLine[0], splitLine[1], splitLine[2]);
    }

    public List<Racer> getAbbreviationRacerList() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Path.of("src/main/resources/application.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (var lines = Files.lines(Path.of(properties.getProperty("abbreviations")))) {
            return lines.map(AbbreviationsExtractor::createRacer).toList();
        } catch (IOException x) {
            throw new RuntimeException("File could not be read", x);
        }
    }
}
