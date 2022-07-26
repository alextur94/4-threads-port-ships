package com.epam.jwd.service.create;

import com.epam.jwd.repository.Port;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 *
 * This class create new port
 */

public class CreatePort {
    private Port port;

    public void addPort() {
        this.port = new Port();
    }

    public Port getPort() {
        return port;
    }
}
