package com.epam.jwd.service.parser;

import com.epam.jwd.repository.Port;
import com.epam.jwd.repository.Ship;
import com.epam.jwd.service.validator.FileValidator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aleksey Turkov
 * @version 1.0 (15/09/2021)
 *
 * This class imports all ships from a file into a sheet
 */

public class ImportListShips {
    private static final Logger logger = LogManager.getLogger(FileValidator.class);
    private final FileValidator fileValidator = new FileValidator();
    public static int countShip;

    public void parser(Port port) throws IOException {
        BufferedReader br = null;
        String string;
        try {
            br = new BufferedReader(new FileReader("src\\main\\resources\\ships.txt"));
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
        while ((string = br.readLine()) != null) {
            String[] ships = string.split(" ");
            countShip++;
            if (fileValidator.shipValidate(ships)) {
                port.addShipQueue(new Ship(ships[0], Integer.parseInt(ships[1]), Integer.parseInt(ships[2])));
            }
        }
    }
}
