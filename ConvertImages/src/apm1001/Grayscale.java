package apm1001;
import edu.duke.*;

import java.io.File;

public class Grayscale {

    ImageResource convertToGrayscale (ImageResource image) {
        ImageResource grayscaleImage = new ImageResource(image.getWidth(), image.getHeight());

        for (Pixel px : image.pixels()) {
            Pixel newPx = grayscaleImage.getPixel(px.getX(), px.getY());

            int average = (px.getBlue() + px.getRed() + px.getGreen()) / 3;

            newPx.setRed(average);
            newPx.setGreen(average);
            newPx.setBlue(average);
        }
        return grayscaleImage;
    }

    ImageResource makeInverted (ImageResource image) {
        ImageResource grayscaleImage = new ImageResource(image.getWidth(), image.getHeight());

        for (Pixel px : image.pixels()) {
            Pixel newPx = grayscaleImage.getPixel(px.getX(), px.getY());

            int invRed = 255 - px.getRed();

            newPx.setRed(255 - px.getRed());
            newPx.setGreen(255 - px.getGreen());
            newPx.setBlue(255 - px.getBlue());
        }
        return grayscaleImage;
    }
    void testGray () {
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            ImageResource image = new ImageResource(f);
            ImageResource grayImage = convertToGrayscale(image);

            String newName = "gray-" + f.getName();
            grayImage.setFileName(newName);
            grayImage.save();
            grayImage.draw();
        }
    }

    void testInversion () {
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            ImageResource image = new ImageResource(f);
            ImageResource grayImage = makeInverted(image);

            String newName = "inverted-" + f.getName();
            grayImage.setFileName(newName);
            grayImage.save();
            grayImage.draw();
        }
    }
}
