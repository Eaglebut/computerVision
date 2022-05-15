package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

@Log4j2
public class FifthLabTest extends LabTest {

    public static final String LAB_PATH = "lab5/";
    public static final String TEST_IMAGE_GEOMETRY = "geometry.jpg";
    public static final String TEST_IMAGE_MANULS = "manuls.jpg";


    @Test
    public void task1() {
        double initVal = 50;
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_GEOMETRY);
        Mat flooded = conversionService.fillFlood(
                image,
                new Point(0, 0),
                new Scalar(initVal, initVal, initVal),
                new Scalar(initVal, initVal, initVal)
        );
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "floood", flooded);
    }

    @Test
    public void task2() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_MANULS);
        for (int i = 1; i < 4; i++) {
            imageService.saveMatToFile(
                    TEST_IMAGE_PATH + LAB_PATH,
                    "pyramidUp " + i,
                    conversionService.pyramidUp(image, i)
            );
        }
        for (int i = 1; i <= 10; i++) {
            imageService.saveMatToFile(
                    TEST_IMAGE_PATH + LAB_PATH,
                    "pyramidDown" + i,
                    conversionService.pyramidDown(image, i)
            );
        }
        Mat cropped = conversionService.resize(image, 400, 400);
        Mat smallImage = conversionService.pyramidDown(cropped, 1);
        Mat resized = conversionService.pyramidUp(smallImage, 1);
        Mat dst = new Mat();
        Core.subtract(cropped, resized, dst);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "substructed",
                dst
        );
    }
}
