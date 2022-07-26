package com.epam.jwd.service.validator;

import com.epam.jwd.service.parser.ImportListShips;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 * <p>
 * The class checks the data from the file for validity.
 * If the data is entered incorrectly, then the error is written
 * to the log and this line is not imported into the program
 */

public class FileValidator {
    private static final Logger logger = LogManager.getLogger(FileValidator.class);

    public boolean shipValidate(String[] ships) {
        if (ships.length == 3) {
            try {
                int num1 = Integer.parseInt(ships[1]);
                int num2 = Integer.parseInt(ships[2]);
                if (num1 >= 0 && num2 >= 0) {
                    if (num1 >= num2) {
                        return true;
                    } else {
                        logger.error("The number of containers is more than allowed (" + ImportListShips.countShip + " str)");
                        return false;
                    }
                } else {
                    logger.error("The number should be positive or zero (" + ImportListShips.countShip + " str)");
                    return false;
                }
            } catch (NumberFormatException e) {
                logger.error("One or two values are not an integer (" + ImportListShips.countShip + " str)");
                return false;
            }
        } else {
            logger.error("The string must be in the format (string int int) (" + ImportListShips.countShip + " str)");
            return false;
        }
    }
}

