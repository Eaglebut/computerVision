package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.Core;
import ru.sfedu.computervision.utils.ConfigurationUtil;
import ru.sfedu.computervision.utils.OSType;

import java.nio.file.Paths;
import java.util.Locale;

@Log4j2
public class OsService {

    private final String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
    private static OsService INSTANCE;

    public static OsService getInstance() {
        if (INSTANCE == null){
            INSTANCE = new OsService();
        }
        return INSTANCE;
    }

    public static void load(){
        getInstance();
    }

    private OsService() {
        try {
            log.info("Checking OS.....");
            loadProp(OSType.getOperatingSystemType(OS).getConstantPath());
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private void loadProp(String path) throws Exception {
        String pathLin = ConfigurationUtil.getConfigurationEntry(path);
        System.load(Paths.get(pathLin).toAbsolutePath().toString());
        log.debug("Properties are loaded ");
        log.debug("OS Version: " + OS);
        log.debug("Open CV version - " + Core.getVersionString());
    }
}