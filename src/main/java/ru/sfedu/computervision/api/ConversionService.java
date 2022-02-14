package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

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
}
