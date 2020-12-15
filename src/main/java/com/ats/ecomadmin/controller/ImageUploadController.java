package com.ats.ecomadmin.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ats.ecomadmin.commons.Constants;
import com.ats.ecomadmin.model.Info;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageUploadController {

	public void saveUploadedFiles(MultipartFile file, int imageType, String imageName) throws IOException {

		Path path = Paths.get(Constants.UPLOAD_URL + imageName);

		byte[] bytes = file.getBytes();

		if (imageType == 1) {

			path = Paths.get(Constants.UPLOAD_URL + imageName);

		}

		Files.write(path, bytes);

	}

	public static Info saveImgFiles(MultipartFile file, String[] allowExt, String imageName) throws IOException {

		Info info = new Info();
		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String[] filetypes = file.getOriginalFilename().split("\\.");
			if (ArrayUtils.contains(allowExt, extension.toLowerCase()) && filetypes.length == 2) {
				Path path = Paths.get(Constants.UPLOAD_URL + imageName);

				byte[] bytes = file.getBytes();

				path = Paths.get(Constants.UPLOAD_URL + imageName);

				Files.write(path, bytes);
				info.setError(false);
				info.setMsg("Upload Successfully ");
			} else {
				info.setError(true);
				info.setMsg("Error While Uploading Image");
			}
		} catch (Exception e) {
			info.setError(true);
			info.setMsg("Error While Uploading Image");
			e.printStackTrace();
		}
		return info;
	}

	
	public static Info saveImgFilesProdImg(MultipartFile file, String[] allowExt, String imageName) throws IOException {

		Info info = new Info();
		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			String[] filetypes = file.getOriginalFilename().split("\\.");
			if (ArrayUtils.contains(allowExt, extension.toLowerCase()) && filetypes.length == 2) {
				Path path = Paths.get(Constants.PROD_IMG_UPLOAD_URL + imageName);

				byte[] bytes = file.getBytes();

				path = Paths.get(Constants.PROD_IMG_UPLOAD_URL + imageName);

				Files.write(path, bytes);
				info.setError(false);
				info.setMsg("prod Image Upload Successfully ");
			} else {
				info.setError(true);
				info.setMsg("Error While Uploading prod Image");
			}
		} catch (Exception e) {
			info.setError(true);
			info.setMsg("Error While Uploading prod Image");
			e.printStackTrace();
		}
		return info;
	}

	
	public Info saveUploadedImgeWithResize(MultipartFile file, String imageName, int width, int hieght)
			throws IOException {

		Info info = new Info();

		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());

			Path path = Paths.get(Constants.UPLOAD_URL + imageName);

			byte[] bytes = file.getBytes();

			Files.write(path, bytes);
			Image img = null;
			BufferedImage tempPNG = null;

			File newFilePNG = null;

			img = ImageIO.read(new File(Constants.UPLOAD_URL + imageName));
			tempPNG = resizeImage(img, width, hieght);

			newFilePNG = new File(Constants.UPLOAD_URL + imageName);

			ImageIO.write(tempPNG, extension, newFilePNG);

			info.setError(false);
			info.setMsg("Upload Successfully ");

		} catch (Exception e) {

			e.printStackTrace();
			info.setError(true);
			info.setMsg("Error While Uploading Image");
		}
		return info;

	}

	// Sachin
	// 28-09-2020
	// to Upload Product Image
	public Info saveProdImgeWithResize(MultipartFile file, String imageName, int width, int hieght) {
		Info info = new Info();
		try {
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());

			Path path = Paths.get(Constants.PROD_IMG_UPLOAD_URL + imageName);

			byte[] bytes = file.getBytes();

			Files.write(path, bytes);
			Image img = null;
			BufferedImage tempPNG = null;

			File newFilePNG = null;
			try {
				System.err.println(" image " + Constants.PROD_IMG_UPLOAD_URL + " name " + imageName);
				img = ImageIO.read(new File(Constants.PROD_IMG_UPLOAD_URL + imageName));
			} catch (Exception e) {
				try {
					tempPNG = resizeImage(img, width, hieght);

					newFilePNG = new File(Constants.PROD_IMG_UPLOAD_URL + imageName);

					ImageIO.write(tempPNG, extension, newFilePNG);

					info.setError(false);
					info.setMsg("Upload Successfully ");
				} catch (Exception e1) {

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			info.setError(true);
			info.setMsg("Error While Uploading Image");
		}
		return info;
	}

	public static BufferedImage resizeImage(final Image image, int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		// below three lines are for RenderingHints for better image quality at cost of
		// higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

}
