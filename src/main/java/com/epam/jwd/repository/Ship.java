package com.epam.jwd.repository;

import java.util.Objects;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 * <p>
 * Each ship has a name, capacity and current number of containers
 */

public class Ship {
    private String nameShip;
    private int maxCountContainers;
    private int currentNumberOfContainers;

    public Ship(String name, int maxContainers, int numberOfContainers) {
        this.nameShip = name;
        this.maxCountContainers = maxContainers;
        this.currentNumberOfContainers = numberOfContainers;
    }

    public String getNameShip() {
        return nameShip;
    }

    public int getCurrentNumberOfContainers() {
        return currentNumberOfContainers;
    }

    public int getFreePlace() {
        return maxCountContainers - currentNumberOfContainers;
    }

    public void addContainers(int number) {
        currentNumberOfContainers += number;
    }

    public void subtractContainers(int number) {
        currentNumberOfContainers -= number;
    }

    @Override
    public String toString() {
        return nameShip + " - " + maxCountContainers + "/" + currentNumberOfContainers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return currentNumberOfContainers == ship.currentNumberOfContainers && nameShip.equals(ship.nameShip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameShip, currentNumberOfContainers);
    }
}

