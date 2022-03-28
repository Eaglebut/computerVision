package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Point;

@Log4j2
public class ThirdLabTest extends LabTest {

    public static final String LAB_PATH = "lab3/";


    @Test
    public void task1() {
        sobelTest();
        laplacianTest();
    }

    @Test
    public void task2() {
        mirrorTest();
        repeatTest();
        resize();
    }

    @Test
    public void task3() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        Mat dst = new Mat();
        conversionService.rotateImage(image, dst, 30, false);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "rotate_", dst);
    }

    @Test
    public void task4() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "changePerspective_",
                conversionService.changePerspective(image, new Point(0, 100),
                        new Point(image.width(), 200),
                        new Point(0, image.height() - 100),
                        new Point(image.width(), image.height() - 200)));
    }

    private void resize() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "resize_",
                conversionService.resize(image, 100, 100)
        );
    }

    private void sobelTest() {
        Mat grayImageMatrix = imageService.getGrayImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "SobelX_",
                conversionService.sobelConversionX(grayImageMatrix)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "SobelY_",
                conversionService.sobelConversionY(grayImageMatrix)
        );
    }

    private void laplacianTest() {
        Mat grayImageMatrix = imageService.getGrayImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "LaplacianX_",
                conversionService.laplacianConversion(grayImageMatrix)
        );
    }

    private void mirrorTest() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "mirrorH_",
                conversionService.mirrorH(image)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "mirrorV_",
                conversionService.mirrorV(image)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "mirrorHV_",
                conversionService.mirrorHV(image)
        );
    }

    private void repeatTest() {
        Mat image = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "repeatVertical_",
                conversionService.repeatVertical(image, 3)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH + LAB_PATH,
                "repeatHorizontal_",
                conversionService.repeatHorizontal(image, 3)
        );
    }
}
