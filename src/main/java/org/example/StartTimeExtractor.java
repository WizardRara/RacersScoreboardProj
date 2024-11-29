package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class StartTimeExtractor {
    public Map<String, LocalTime> getStartTime() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Path.of("src/main/resources/application.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (var lines = Files.lines(Path.of(properties.getProperty("start")))) {
            return lines.map(s -> s.split("_"))
                    .collect(Collectors.toMap(s -> s[0].substring(0, 3), s -> LocalTime.parse(s[1])));
        } catch (IOException x) {
            throw new RuntimeException("File could not be read", x);
        }
    }
}
