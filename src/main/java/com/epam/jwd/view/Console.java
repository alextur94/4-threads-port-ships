package com.epam.jwd.view;

import com.epam.jwd.repository.Port;
import com.epam.jwd.service.action.TransferContainers;

public class Console {
    Port port;
    private long timeBefore;
    private long timeAfter;
    int containersBeforeShip;
    int containersPortBefore;


    public void Console(Port port) {
        this.port = port;
    }

    public void inputOnConsoleBefore() {
        timeBefore = System.currentTimeMillis();
        containersBeforeShip = Port.shipsQueue.stream().mapToInt(sum -> sum.getCurrentNumberOfContainers()).sum();
        containersPortBefore = port.getCurrentNumberOfContainers();
    }

    public void inputOnConsoleAfter() {
        timeAfter = System.currentTimeMillis();
        int containersAfterShip = Port.shipsList.stream().mapToInt(sum -> sum.getCurrentNumberOfContainers()).sum();
        int containersPortAfter = port.getCurrentNumberOfContainers();
        System.out.println("---Program the end. Result: ");
        System.out.println("*Containers on ships before/after: " + containersBeforeShip + "/" + containersAfterShip);
        System.out.println("*Containers in port before/after: " + containersPortBefore + "/" + containersPortAfter);
        System.out.println("*Containers unload/load: " + TransferContainers.unload + "/" + TransferContainers.load);
        System.out.println("*Program time: " + (timeAfter - timeBefore) + " ms");
        System.out.println("*Forecasted time: " + (TransferContainers.unload * 2 + TransferContainers.load) + " ms");
    }
}
