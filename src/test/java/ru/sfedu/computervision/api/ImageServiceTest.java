package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

@Log4j2
class ImageServiceTest {

    private static final String TEST_IMAGE_PATH = "Z:/projects/computerVision/src/main/resources/images/";
    private static final String TEST_IMAGE_NAME = "Z8to82CyVpg.jpg";

    @Test
    void lab1(){
        OsService.load();
    }

    @Test
    void lab2() {
        lab2(3, TEST_IMAGE_PATH, TEST_IMAGE_NAME);
    }

    private Mat lab2(int numberOfChannel, String pathName, String imageName) {
        ImageService imageService = ImageService.getInstance();
        ConversionService convesionService = ConversionService.getInstance();
        Mat imageMatrix = Imgcodecs.imread(pathName + imageName);
        imageService.createImageFrame(imageMatrix);
        convesionService.removeChannel(imageMatrix, numberOfChannel);
        imageService.createImageFrame(imageMatrix);
        imageService.saveMatToFile(pathName, imageMatrix);
        return imageMatrix;
    }
}