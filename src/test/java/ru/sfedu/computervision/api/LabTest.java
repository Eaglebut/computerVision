package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

@Log4j2
class LabTest {

    private static final String TEST_IMAGE_PATH = "Z:/projects/computerVision/src/main/resources/images/";
    private static final String TEST_IMAGE_NAME = "Z8to82CyVpg.jpg";

    private static ImageService imageService;
    private static ConversionService conversionService;

    @BeforeAll
    public static void beforeAll() {
        imageService = ImageService.getInstance();
        conversionService = ConversionService.getInstance();
    }

    @Test
    public void lab1() {
        OsService.load();
    }

    @Test
    public void lab2() {
        lab2(3, TEST_IMAGE_PATH, TEST_IMAGE_NAME);
    }

    private Mat lab2(int numberOfChannel, String pathName, String imageName) {
        Mat imageMatrix = Imgcodecs.imread(pathName + imageName);
        imageService.createImageFrame(imageMatrix);
        conversionService.removeChannel(imageMatrix, numberOfChannel);
        imageService.createImageFrame(imageMatrix);
        imageService.saveMatToFile(pathName, "lab2", imageMatrix);
        return imageMatrix;
    }

    @Test
    public void lab3() {
        lab3Task1();
        lab3Task2();
    }

    private void lab3Task1(){
        sobelTest();
        laplacianTest();
    }

    private void lab3Task2(){
        mirrorTest();
        repeatTest();
    }

    private void sobelTest() {
        Mat grayImageMatrix = imageService.getGrayImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_NAME);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "SobelX_",
                conversionService.sobelConversionX(grayImageMatrix)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "SobelY_",
                conversionService.sobelConversionY(grayImageMatrix)
        );
    }

    private void laplacianTest() {
        Mat grayImageMatrix = imageService.getGrayImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_NAME);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "LaplacianX_",
                conversionService.laplacianConversion(grayImageMatrix)
        );
    }

    private void mirrorTest(){
        Mat image = Imgcodecs.imread(TEST_IMAGE_PATH + TEST_IMAGE_NAME, Imgcodecs.IMREAD_COLOR);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "mirrorH_",
                conversionService.mirrorH(image)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "mirrorV_",
                conversionService.mirrorV(image)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "mirrorHV_",
                conversionService.mirrorHV(image)
        );
    }

    private void repeatTest(){
        Mat image = Imgcodecs.imread(TEST_IMAGE_PATH + TEST_IMAGE_NAME, Imgcodecs.IMREAD_COLOR);
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "repeatVertical_",
                conversionService.repeatVertical(image, 3)
        );
        imageService.saveMatToFile(
                TEST_IMAGE_PATH,
                "repeatHorizontal_",
                conversionService.repeatHorizontal(image, 3)
        );
    }
}