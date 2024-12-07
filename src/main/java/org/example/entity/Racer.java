package org.example.entity;

import java.util.Objects;

public class Racer {
    private final String abbreviation;
    private final String name;
    private final String rota;
    private String lapTime;

    public Racer(String abbreviation, String name, String rota) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.rota = rota;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getRota() {
        return rota;
    }

    public String getLapTime() {
        return lapTime;
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Racer racer = (Racer) o;
        return Objects.equals(abbreviation, racer.abbreviation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(abbreviation);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(rota);
        result = 31 * result + Objects.hashCode(lapTime);
        return result;
    }

    @Override
    public String toString() {
        return "Racer{" +
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", rota='" + rota + '\'' +
                ", lapTime=" + lapTime +
                '}';
    }
}
