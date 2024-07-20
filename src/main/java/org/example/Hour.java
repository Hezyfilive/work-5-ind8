package org.example;

import java.time.LocalDateTime;
import java.util.Objects;

public class Hour implements Comparable<Hour> {
    private int passengerCount;
    private String comment;
    private LocalDateTime dateTime;

    public Hour() {

    }

    public Hour(int passengerCount, String comment, LocalDateTime dateTime) {
        this.passengerCount = passengerCount;
        this.comment = comment;
        this.dateTime = dateTime;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Hour{passengerCount=" + passengerCount + ", comment='" + comment + "', dateTime=" + dateTime + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hour hour = (Hour) o;
        return passengerCount == hour.passengerCount && Objects.equals(comment, hour.comment) && Objects.equals(dateTime, hour.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerCount, comment, dateTime);
    }

    @Override
    public int compareTo(Hour o) {
        return Integer.compare(this.passengerCount, o.passengerCount);
    }
}
