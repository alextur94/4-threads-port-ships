package com.epam.jwd.service.create;

import com.epam.jwd.repository.Port;
import com.epam.jwd.service.parser.ImportListShips;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * @author Aleksey Turkov
 * @version 1.0 (27/09/2021)
 *
 * This class start import form file
 */

public class ImportFile {
    private static final Logger logger = LogManager.getLogger(ImportFile.class);

    public void importFile(Port port) {
        ImportListShips input = new ImportListShips();
        try {
            input.parser(port);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
