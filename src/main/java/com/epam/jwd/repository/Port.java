package com.epam.jwd.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 *
 * Each port has a warehouse, and information about the number of ships that is in it
 */

public class Port {
    public static Queue<Ship> shipsQueue = new LinkedList<>();
    public static List<Ship> shipsList = new ArrayList<>();
    private int maxCountContainers = 30000;
    private int currentNumberOfContainers = 10000;
    private int currentNumberOfContainersInTempStorage;

    public synchronized void addShipQueue(Ship ship) {
        shipsQueue.add(ship);
    }

    public synchronized static Ship getElementQueue() {
        Ship ship = shipsQueue.remove();
        addList(ship);
        return ship;
    }

    public synchronized static void addList(Ship ship) {
        shipsList.add(ship);
    }

    public synchronized int getCurrentNumberOfContainers() {
        return currentNumberOfContainers;
    }

    public synchronized int getFreePlace() {
        return maxCountContainers - currentNumberOfContainers;
    }

    public synchronized void addContainers(int number) {
        currentNumberOfContainers += number;
    }

    public synchronized void subtractContainers(int number) {
        currentNumberOfContainers -= number;
    }

    public synchronized int getCurrentNumberOfContainersInTempStorage() {
        return currentNumberOfContainersInTempStorage;
    }

    public synchronized void setCurrentNumberOfContainersInTempStorage(int currentNumberOfContainersInTempStorage) {
        this.currentNumberOfContainersInTempStorage = currentNumberOfContainersInTempStorage;
    }
}

