package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.Mat;

@Log4j2
public class ConversionService {

    private static ConversionService instance;

    public static ConversionService getInstance(){
        if (instance == null){
            instance = new ConversionService();
        }
        return instance;
    }

    private ConversionService(){
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
}
