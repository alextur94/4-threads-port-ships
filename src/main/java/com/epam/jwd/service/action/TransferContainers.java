package com.epam.jwd.service.action;

import com.epam.jwd.repository.Container;
import com.epam.jwd.repository.Port;
import com.epam.jwd.repository.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 * <p>
 * This class contains the logic of all actions with
 * containers - unloading, loading, moving to a warehouse,
 * moving from a warehouse, moving to another ship.
 */

public class TransferContainers {
    private static final Logger logger = LogManager.getLogger(TransferContainers.class);
    private Object lock1 = new Object();
    private Object lock2 = new Object();
    private Object lock3 = new Object();
    private Object lock4 = new Object();
    private Object lock5 = new Object();
    private Random random = new Random();
    private Port port;
    private Ship ship;
    public static int unload;
    public static int load;

    public void setValue(Port port, Ship ship) {
        this.port = port;
        this.ship = ship;
    }

    public void chooseAction(int action) {
        switch (action) {
            case 0:
                unloadFromShipToWarehouse();
                break;
            case 1:
                loadToShipFromWarehouse();
                break;
            case 2:
                unloadFromShipToShip();
                break;
            case 3:
                loadFromShipToShip();
                break;
            case 4:
                unloadFromShipToWarehouse();
                loadToShipFromWarehouse();
                break;
            case 5:
                unloadFromShipToWarehouse();
                loadFromShipToShip();
                break;
            case 6:
                unloadFromShipToShip();
                loadToShipFromWarehouse();
                break;
        }
    }

    public void unloadFromShipToWarehouse() {
        synchronized (lock1) {
            int currentContainersInShip = ship.getCurrentNumberOfContainers();
            if (currentContainersInShip != 0) {
                int numberOfContainersForTransfer = randomValue(currentContainersInShip);
                int freePlaceInWarehouse = port.getFreePlace();
                boolean capacityCheck = capacityCheck(numberOfContainersForTransfer, freePlaceInWarehouse);
                if (capacityCheck) {
                    ship.subtractContainers(numberOfContainersForTransfer);
                    port.addContainers(numberOfContainersForTransfer);
                    unload += numberOfContainersForTransfer;
                    try {
                        Thread.sleep(Container.CONTAINERUNLOADINGSPEED * numberOfContainersForTransfer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.info("The ship " + ship.getNameShip() + " is not unloaded! not enough space in the warehouse. Needed/Available " + numberOfContainersForTransfer + "/" + freePlaceInWarehouse);
                }
            } else {
                logger.info(ship.getNameShip() + " ship empty");
            }
        }
    }

    public void unloadFromShipToShip() {
        synchronized (lock2) {
            int tempStorage = port.getCurrentNumberOfContainersInTempStorage();
            if (tempStorage == 0) {
                int countContainers = randomValue(ship.getCurrentNumberOfContainers());
                port.setCurrentNumberOfContainersInTempStorage(countContainers);
                ship.subtractContainers(countContainers);
                unload+=countContainers;
                try {
                    Thread.sleep(Container.CONTAINERUNLOADINGSPEED * countContainers);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                unloadFromShipToWarehouse();
            }
        }
    }

    public void loadFromShipToShip() {
        synchronized (lock3) {
            int temporaryStorageOfContainers = port.getCurrentNumberOfContainersInTempStorage();
            if (temporaryStorageOfContainers != 0) {
                int freePlaceInShip = ship.getFreePlace();
                boolean capacityCheck = capacityCheck(temporaryStorageOfContainers, freePlaceInShip);
                if (capacityCheck) {
                    ship.addContainers(temporaryStorageOfContainers);
                    port.setCurrentNumberOfContainersInTempStorage(0);
                    load+= temporaryStorageOfContainers;
                    try {
                        Thread.sleep(Container.CONTAINERLOADINGSPEED * temporaryStorageOfContainers);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.info(ship.getNameShip() + " container loading error. Too much!");
                }
            } else {
                logger.info("No containers from ship to load in " + ship.getNameShip());
            }
        }
    }

    public void loadToShipFromWarehouse() {
        synchronized (lock4) {
            int currentContainersInPort = port.getCurrentNumberOfContainers();
            if (currentContainersInPort != 0) {
                int numberOfContainersForTransfer = randomValue(currentContainersInPort) / 200;
                int freePlaceInShip = ship.getFreePlace();
                boolean capacityCheck = capacityCheck(numberOfContainersForTransfer, freePlaceInShip);
                if (capacityCheck) {
                    ship.addContainers(numberOfContainersForTransfer);
                    port.subtractContainers(numberOfContainersForTransfer);
                    load += numberOfContainersForTransfer;
                    try {
                        Thread.sleep(Container.CONTAINERLOADINGSPEED * numberOfContainersForTransfer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.info("Ship " + ship.getNameShip() + ": insufficient storage space - Needed/Available " + numberOfContainersForTransfer + "/" + freePlaceInShip);
                }
            } else {
                logger.info(ship.getNameShip() + " ship loading error. There are no containers in the port");
            }
        }
    }

    public void temporaryStorageOfContainersIsEmpty() {
        synchronized (lock5) {
            int temporaryStorageOfContainers = port.getCurrentNumberOfContainersInTempStorage();
            if (temporaryStorageOfContainers != 0) {
                int freePlaceInPort = port.getFreePlace();
                boolean freePlace = capacityCheck(temporaryStorageOfContainers, freePlaceInPort);
                if (freePlace) {
                    port.addContainers(temporaryStorageOfContainers);
                    port.setCurrentNumberOfContainersInTempStorage(0);
                    unload+=temporaryStorageOfContainers;
                    try {
                        Thread.sleep(Container.CONTAINERUNLOADINGSPEED * temporaryStorageOfContainers);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.error("Attention! Containers remain in the temporary warehouse! The main warehouse is full!");
                }
            }
        }
    }

    public synchronized int randomValue(int number) {
        if (number > 0) {
            return random.nextInt(number);
        } else {
            return 0;
        }
    }

    public synchronized boolean capacityCheck(int numberContainers, int freePlace) {
        return freePlace >= numberContainers;
    }

    public synchronized int countFreePlace(int max, int current) {
        return max - current;
    }
}

