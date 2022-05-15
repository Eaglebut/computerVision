package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

@Log4j2
public class FifthLabTest extends LabTest {

    public static final String LAB_PATH = "lab5/";
    public static final String TEST_IMAGE_GEOMETRY = "geometry.jpg";
    public static final String TEST_IMAGE_MANULS = "manuls.jpg";
    public static final String TEST_IMAGE_RECTANGLES = "rectangles.jpg";


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
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "substructed",
                conversionService.subtractImages(cropped, resized)
        );
    }

    @Test
    public void task3() {
        Mat img = new Mat(1000, 1000, CvType.CV_8UC3, new Scalar(255, 255, 255));
        Imgproc.rectangle(img, new Point(0, 0), new Point(100, 100),
                conversionService.getRandomColor(), Core.FILLED);
        Imgproc.rectangle(img, new Point(200, 0), new Point(300, 100),
                conversionService.getRandomColor(), Core.FILLED);
        Imgproc.rectangle(img, new Point(100, 150), new Point(500, 500),
                conversionService.getRandomColor(), Core.FILLED);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "rectangleSource", img);
        List<Mat> rectangles = conversionService.findRectangles(img, 100, 100);
        rectangles.forEach(mat -> imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "rectangle" + mat.hashCode(), mat));
        Assertions.assertEquals(2, rectangles.size());
    }

}
