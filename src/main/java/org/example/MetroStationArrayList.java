package org.example;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MetroStationArrayList extends BaseEntity {
    private List<Hour> hours;

    public MetroStationArrayList() {
        super(null, 0);
        this.hours = new ArrayList<>();
    }

    public MetroStationArrayList(String name, int year) {
        super(name, year);
        this.hours = new ArrayList<>();
    }

    @Override
    public List<Hour> getSequence() {
        return new ArrayList<>(hours);
    }

    @Override
    public void addHour(Hour hour) {
        hours.add(hour);
    }

    @Override
    public void removeHour(Hour hour) {
        hours.remove(hour);
    }

    @Override
    public List<Hour> findHoursWithMinPassengers() {
        int minPassengers = hours.stream().mapToInt(Hour::getPassengerCount).min().orElse(0);
        return hours.stream().filter(hour -> hour.getPassengerCount() == minPassengers).collect(Collectors.toList());
    }

    @Override
    public List<Hour> findHoursWithMaxCommentLength() {
        int maxCommentLength = hours.stream().mapToInt(hour -> hour.getComment().length()).max().orElse(0);
        return hours.stream().filter(hour -> hour.getComment().length() == maxCommentLength).collect(Collectors.toList());
    }

    @Override
    public void sortHoursByPassengerCount() {
        hours.sort(Comparator.comparingInt(Hour::getPassengerCount).reversed());
    }

    @Override
    public void sortHoursByCommentLength() {
        hours.sort(Comparator.comparingInt(hour -> hour.getComment().length()));
    }

    @Override
    public void sortByCollator() {
        Collator collator = Collator.getInstance(Locale.getDefault());
        hours.sort((h1, h2) -> collator.compare(h1.getComment(), h2.getComment()));
    }

    @Override
    public List<Hour> searchComments(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return hours.stream().filter(hour -> pattern.matcher(hour.getComment()).find()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "MetroStationArrayList{" +
                "name='" + getName() + '\'' +
                ", year=" + getYear() +
                ", hours=" + hours +
                '}';
    }
}
