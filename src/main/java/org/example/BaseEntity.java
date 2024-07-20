package org.example;

import java.util.*;

public abstract class BaseEntity {
    protected String name;
    protected int year;


    public BaseEntity(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getName(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("translations", locale);
            return bundle.getString(name);
        } catch (MissingResourceException e) {
            return name;
        }
    }

    public abstract List<Hour> getSequence();
    public abstract void addHour(Hour hour);
    public abstract void removeHour(Hour hour);
    public abstract List<Hour> findHoursWithMinPassengers();
    public abstract List<Hour> findHoursWithMaxCommentLength();
    public abstract void sortHoursByPassengerCount();
    public abstract void sortHoursByCommentLength();
    public abstract void sortByCollator();
    public abstract List<Hour> searchComments(String regex);
}
