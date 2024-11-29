package org.example;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class LapTimeCalculator {
    public Map<String, Duration> getLapTime() {
        StartTimeExtractor startTimeExtractor = new StartTimeExtractor();
        Map<String, LocalTime> startTime = startTimeExtractor.getStartTime();
        EndTimeExtractor endTimeExtractor = new EndTimeExtractor();
        Map<String, LocalTime> endTime = endTimeExtractor.getEndTime();
        Map<String, Duration> lapTime = new HashMap<>();
        endTime.forEach((key, endTimes) -> startTime.merge(key, endTimes, (startTimes, endTimesValue) -> {
            Duration duration = Duration.between(startTimes, endTimesValue);
            lapTime.put(key, duration);
            return startTimes;
        }));
        return lapTime;
    }
}
