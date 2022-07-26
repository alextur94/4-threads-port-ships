package com.epam.jwd.service.thread;

import com.epam.jwd.repository.Port;
import com.epam.jwd.service.action.ActionWithContainers;
import com.epam.jwd.service.action.TransferContainers;

public class MultiThread extends Thread {
    private TransferContainers transferContainers = new TransferContainers();
    private ActionWithContainers action = new ActionWithContainers();
    private Port port;

    public void Action(Port port) {
        this.port = port;
    }


    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            doWork();
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            doWork();
        }
    });

    Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {
            doWork();
        }
    });

    public void startThread() {
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void joinThread() throws InterruptedException {
        thread1.join();
        thread2.join();
        thread3.join();
    }

    public void doWork() {
        while (!Port.shipsQueue.isEmpty()) {
            transferContainers.setValue(port, Port.getElementQueue());
            transferContainers.chooseAction(action.doActionWithContainers());
        }
        if (Port.shipsList.isEmpty()) finishAdction();
    }

    public void finishAdction() {
        transferContainers.temporaryStorageOfContainersIsEmpty();
    }

}
