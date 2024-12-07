package org.example.repository;

import org.example.entity.Racer;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface RacerStatisticReader {
    List<Racer> getRacersList();

    Map<String, LocalTime> getRacerStartTime();

    Map<String, LocalTime> getRacerEndTime();
}
