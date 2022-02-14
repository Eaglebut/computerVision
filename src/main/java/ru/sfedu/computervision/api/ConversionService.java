package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class ConversionService {

    private static ConversionService instance;

    public static ConversionService getInstance() {
        if (instance == null) {
            instance = new ConversionService();
        }
        return instance;
    }

    private ConversionService() {
        OsService.load();
    }

    public Mat removeChannel(Mat image, int numberOfChannel) {
        int totalBytes = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[totalBytes];
        image.get(0, 0, buffer);
        for (int i = 0; i < totalBytes; i++) {
            if (i % numberOfChannel == 0) {
                buffer[i] = 0;
            }
        }
        image.put(0, 0, buffer);
        return image;
    }

    public Mat sobelConversionX(Mat image) {
        Mat dstSobelX = new Mat();
        Imgproc.Sobel(image, dstSobelX, CvType.CV_32F, 1, 0);
        return dstSobelX;
    }

    public Mat sobelConversionY(Mat image) {
        Mat dstSobelY = new Mat();
        Imgproc.Sobel(image, dstSobelY, CvType.CV_32F, 0, 1);
        return dstSobelY;
    }

    public Mat laplacianConversion(Mat image) {
        Mat dstLaplacian = new Mat();
        Imgproc.Laplacian(image, dstLaplacian, CvType.CV_32F);
        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(dstLaplacian, absLaplacian);
        return absLaplacian;
    }

    public Mat mirrorV(Mat image) {
        Mat dstV = new Mat();
        Core.flip(image, dstV, 0);
        return dstV;
    }

    public Mat mirrorH(Mat image) {
        Mat dstH = new Mat();
        Core.flip(image, dstH, 1);
        return dstH;
    }

    public Mat mirrorHV(Mat image) {
        Mat dstHV = new Mat();
        Core.flip(image, dstHV, -1);
        return dstHV;
    }

    public Mat repeatVertical(Mat image, int amount) {
        List<Mat> images = IntStream.range(0, amount).mapToObj(i -> image).collect(Collectors.toList());
        Mat repeated = new Mat();
        Core.vconcat(images, repeated);
        return repeated;
    }

    public Mat repeatHorizontal(Mat image, int amount) {
        List<Mat> images = IntStream.range(0, amount).mapToObj(i -> image).collect(Collectors.toList());
        Mat repeated = new Mat();
        Core.hconcat(images, repeated);
        return repeated;
    }

}
