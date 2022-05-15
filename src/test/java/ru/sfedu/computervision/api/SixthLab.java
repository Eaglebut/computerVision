package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.AbstractMap.SimpleEntry;

@Log4j2
public class SixthLab extends LabTest {

    public static final String LAB_PATH = "lab6/";
    public static final String TEST_IMAGE_ART = "art.jpg";


    @Test
    public void task1() {
        Mat grayImage = imageService.getGrayImageMatrix(TEST_IMAGE_PATH, TEST_IMAGE_ART);
        Mat detectedEdges = conversionService.baseBlur(grayImage, new Size(3, 3));
        SimpleEntry<Mat, Double> thresholdResult = conversionService.thresholdImage(detectedEdges, 0, 255);
        double threshold = thresholdResult.getValue();
        Imgproc.Canny(detectedEdges, detectedEdges, threshold, threshold * 3);
        imageService.saveMatToFile(TEST_IMAGE_PATH + LAB_PATH, "canny", detectedEdges);
    }
}
