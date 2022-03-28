package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class FourthLabTest extends LabTest {

    public static final String LAB_PATH = "lab4/";
    public static final String TEST_IMAGE_1080P = "1080P.jpg";
    public static final String TEST_IMAGE_CODE = "code.jpg";
    public static final String TEST_IMAGE_SIGN = "dontpoop.jpg";

    @Test
    void baseBlur() {
        Size size = new Size(15, 15);
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "baseblur",
                conversionService.baseBlur(testImage, size));
    }

    @Test
    void gaussianBlur() {
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        Size size = new Size(11, 11);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "gaussianblur",
                conversionService.gaussianBlur(testImage, size, 90, 90, 2));
    }

    @Test
    void medianBlur() {
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_CAT);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "median",
                conversionService.medianBlur(testImage, 11));
    }

    @Test
    void bilateralFilter() {
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_1080P);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "bilateral",
                conversionService.bilateralFilter(testImage, 15, 80, 80, Core.BORDER_DEFAULT));
    }

    @Test
    void blurAll() {
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_SIGN);
        Size size = new Size(11, 11);

        Mat baseBlured = conversionService.baseBlur(testImage, size);
        imageService.createImageFrame(baseBlured);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "baseBlured", baseBlured);

        Mat gaussianBlured = conversionService.gaussianBlur(baseBlured, size, 90, 90, 2);
        imageService.createImageFrame(gaussianBlured);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "baseBlured_gaussian", gaussianBlured);

        Mat medianBlured = conversionService.medianBlur(gaussianBlured, (int) size.width);
        imageService.createImageFrame(medianBlured);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "baseBlured_gaussian_median",
                medianBlured);

        Mat bilateralBlured = conversionService.bilateralFilter(medianBlured,
                15,
                80,
                80,
                Core.BORDER_DEFAULT);
        imageService.createImageFrame(bilateralBlured);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH,
                "baseBlured_gaussian_median_bilateral",
                bilateralBlured);
    }


    @Test
    void morfEllipse() {
        List<Double> sizes = Arrays.asList(3.0, 5.0, 7.0, 9.0, 13.0, 15.0);
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_SIGN);
        sizes.forEach(size -> testMorph(size, testImage.clone(), Imgproc.MORPH_ELLIPSE, "ellipse"));
    }

    @Test
    void morfRect() {
        List<Double> sizes = Arrays.asList(3.0, 5.0, 7.0, 9.0, 13.0, 15.0);
        Mat testImage = imageService.getImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_SIGN);
        sizes.forEach(size -> testMorph(size, testImage.clone(), Imgproc.MORPH_RECT, "rectangle"));
    }


    private void testMorph(double size, Mat srcImage, int shape, String suffix) {
        Mat morph = Imgproc.getStructuringElement(shape, new Size(size, size));

        Mat dilated = conversionService.dilate(srcImage, morph);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "mrf_dilate_" + suffix + size, dilated);

        Mat gradient = conversionService.morphGradient(srcImage, morph);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "mrf_gradient_" + suffix + size, gradient);

        conversionService.morphBlackHat(srcImage, morph);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "mrf_blackhat_" + suffix + size, gradient);

    }
}
