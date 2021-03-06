package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

@Log4j2
public class SecondLabTest extends LabTest {

    public static final String LAB_PATH = "lab2/";

    @Test
    public void lab2() {
        lab2(20, TEST_IMAGE_PATH, LAB_PATH, TEST_IMAGE_CAT);
    }

    private Mat lab2(int numberOfChannel, String pathName, String labPathName, String imageName) {
        Mat imageMatrix = Imgcodecs.imread(pathName + imageName);
        imageService.createImageFrame(imageMatrix);
        conversionService.removeChannel(imageMatrix, numberOfChannel);
        imageService.createImageFrame(imageMatrix);
        imageService.saveMatToFile(pathName + labPathName, "lab2", imageMatrix);
        return imageMatrix;
    }
}
