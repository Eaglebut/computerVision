package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class ConversionService {

    public static final int MAX_COLOR = 255;
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

    public Mat resize(Mat image, int x, int y) {
        Mat dst = new Mat();
        Imgproc.resize(image, dst, new Size(x, y));
        return dst;
    }

    public void rotateImage(Mat imageSrc, Mat imageDst, double angle, boolean cutImage) {
        Point center = new Point(imageSrc.width() / 2.0, imageSrc.height() / 2.0);
        double scale = 1;
        if (!cutImage) {
            double size = Math.sqrt(imageSrc.width() * imageSrc.width() + imageSrc.height() * imageSrc.height());
            double scaleX = imageSrc.width() / size;
            double scaleY = imageSrc.height() / size;
            scale = Math.min(scaleX, scaleY);
        }
        Mat rotationMat = Imgproc.getRotationMatrix2D(
                center,
                angle,
                scale
        );
        Imgproc.warpAffine(imageSrc, imageDst, rotationMat, imageSrc.size());
    }

    /**
     * transform rectangle with vertices ABCD
     */
    public Mat changePerspective(Mat imageSrc,
                                 Point targetA,
                                 Point targetB,
                                 Point targetC,
                                 Point targetD) {
        Mat dst = new Mat();
        List<Point> pointsSrc = Arrays.asList(new Point(0, 0),
                new Point(imageSrc.width(), 0),
                new Point(0, imageSrc.height()),
                new Point(imageSrc.width(), imageSrc.height()));
        List<Point> pointsDst = Arrays.asList(targetA, targetB, targetC, targetD);
        Mat srcPointMat = Converters.vector_Point_to_Mat(pointsSrc, CvType.CV_32F);
        Mat dstPointMat = Converters.vector_Point_to_Mat(pointsDst, CvType.CV_32F);
        Mat perspectiveTransform = Imgproc.getPerspectiveTransform(srcPointMat, dstPointMat);
        Imgproc.warpPerspective(imageSrc, dst, perspectiveTransform, imageSrc.size());
        return dst;
    }

    public Mat baseBlur(Mat src, Size kSize) {
        Mat dst = new Mat();
        Imgproc.blur(src, dst, kSize, new Point(-1, -1));
        return dst;
    }

    public Mat gaussianBlur(Mat src, Size ksize, double sigmaX, double sigmaY, int borderType) {
        Mat dst = new Mat();
        Imgproc.GaussianBlur(src, dst, ksize, sigmaX, sigmaY, borderType);
        return dst;
    }

    public Mat medianBlur(Mat src, int ksize) {
        Mat dst = new Mat();
        Imgproc.medianBlur(src, dst, ksize);
        return dst;
    }

    public Mat bilateralFilter(Mat src, int d, double sigmaColor, double sigmaSpace, int borderType) {
        Mat dst = new Mat();
        Imgproc.bilateralFilter(src, dst, d, sigmaColor, sigmaSpace, borderType);
        return dst;
    }

    public Mat dilate(Mat src, Mat morphEllipse) {
        Mat dst = src.clone();
        Imgproc.dilate(src, dst, morphEllipse);
        return dst;
    }

    public Mat morphGradient(Mat src, Mat morph) {
        Mat dst = src.clone();
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_GRADIENT, morph);
        return dst;
    }

    public Mat morphBlackHat(Mat src, Mat morph) {
        Mat dst = src.clone();
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_BLACKHAT, morph);
        return dst;
    }

    public Mat fillFlood(Mat srcImage,
                         Point startPoint,
                         Scalar topColorBorder,
                         Scalar bottomColorBorder) {
        return fillFlood(srcImage, startPoint, null, topColorBorder, bottomColorBorder);
    }

    public Mat fillFlood(Mat srcImage,
                         Point startPoint,
                         Scalar fillColor,
                         Scalar topColorBorder,
                         Scalar bottomColorBorder) {
        if (fillColor == null) {
            Random random = new Random();
            fillColor = new Scalar(
                    random.nextDouble(0, MAX_COLOR),
                    random.nextDouble(0, MAX_COLOR),
                    random.nextDouble(0, MAX_COLOR)
            );
        }
        Imgproc.floodFill(
                srcImage,
                new Mat(),
                startPoint,
                fillColor,
                new Rect(),
                topColorBorder,
                bottomColorBorder,
                Imgproc.FLOODFILL_FIXED_RANGE
        );
        return srcImage;
    }

    public Mat pyramidDown(Mat srcImage, int amount) {
        Mat result = new Mat();
        if (amount > 0) {
            Imgproc.pyrDown(srcImage, result);
        }
        for (int i = 1; i < amount; i++) {
            Imgproc.pyrDown(result, result);
        }
        return result;
    }

    public Mat pyramidUp(Mat srcImage, int amount) {
        Mat result = new Mat();
        if (amount > 0) {
            Imgproc.pyrUp(srcImage, result);
        }
        for (int i = 1; i < amount; i++) {
            Imgproc.pyrUp(result, result);
        }
        return result;
    }

}
