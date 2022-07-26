package com.epam.jwd.service;

import com.epam.jwd.service.create.CreatePort;
import com.epam.jwd.service.create.ImportFile;
import com.epam.jwd.service.thread.MultiThread;
import com.epam.jwd.view.Console;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 * <p>
 * Threads are created in the class and the logic
 * of actions of operations with the ship is transferred to them
 */

public class Run {
    private CreatePort port = new CreatePort();
    private Console console = new Console();
    private MultiThread multiThread = new MultiThread();

    public void begin() throws InterruptedException {
        port.addPort();
        new ImportFile().importFile(port.getPort());

        console.Console(port.getPort());
        console.inputOnConsoleBefore();

        multiThread.Action(port.getPort());
        multiThread.startThread();
        multiThread.joinThread();

        console.inputOnConsoleAfter();
    }
}
