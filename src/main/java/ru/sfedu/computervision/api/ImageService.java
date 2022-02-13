package ru.sfedu.computervision.api;

import lombok.extern.log4j.Log4j2;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

@Log4j2
public class ImageService {

	public static final int IMAGE_MARGIN = 50;

	private static ImageService instance;

	public static ImageService getInstance() {
		if (instance == null) {
			instance = new ImageService();
		}
		return instance;
	}

	private ImageService() {
		OsService.load();
	}

	public void saveMatToFile(String filePath, Mat img) {
		try {
			String modFilePath = buildImageName(filePath);
			Imgcodecs.imwrite(modFilePath, img);
		} catch (Exception e) {
			log.debug("only .JPG and .PNG files are supported");
		}
	}

	public BufferedImage matrixToBufferedImage(Mat matrixImage) {
		int type = matrixImage.channels() > 1 ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
		BufferedImage bufferedImage = new BufferedImage(matrixImage.cols(), matrixImage.rows(), type);
		matrixImage.get(0, 0, ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData());
		return bufferedImage;
	}

	public void createImageFrame(Mat matrixImage) {
		createImageFrame(matrixToBufferedImage(matrixImage));
	}

	public void createImageFrame(BufferedImage bufferedImage) {
		Icon icon = new ImageIcon(bufferedImage);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(
				bufferedImage.getWidth(null) + IMAGE_MARGIN,
				bufferedImage.getHeight(null) + IMAGE_MARGIN
		);
		JLabel label = new JLabel();
		label.setIcon(icon);
		frame.add(label);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private String buildImageName(String base) {
		return String.format("%s%d.jpg", base, System.nanoTime());
	}
}
