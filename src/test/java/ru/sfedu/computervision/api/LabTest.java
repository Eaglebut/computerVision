package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;

@Log4j2
abstract class LabTest {

    protected static final String TEST_IMAGE_PATH = "Z:/projects/computerVision/src/main/resources/images/";
    protected static final String TEST_IMAGE_NAME = "Z8to82CyVpg.jpg";

    protected static ImageService imageService;
    protected static ConversionService conversionService;

    @BeforeAll
    public static void beforeAll() {
        imageService = ImageService.getInstance();
        conversionService = ConversionService.getInstance();
    }

}