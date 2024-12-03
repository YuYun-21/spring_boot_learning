package com.yuyun.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageMerger {

    public static void main(String[] args) {
        // 假设有一些图片的URL
        String[] imageUrls = {
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCdSeAUxzCAABx9MfUBg4891.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcxKAHbEKAABx9MfUBg4818.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcsaAfVLuAABx9MfUBg4542.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcxKAHbEKAABx9MfUBg4818.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcsaAfVLuAABx9MfUBg4542.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcxKAHbEKAABx9MfUBg4818.png",
                "http://192.168.100.235:8888/group1/M00/00/24/wKhk62YCcsaAfVLuAABx9MfUBg4542.png"
        };

        try {
            BufferedImage mergedImage = mergeImages(imageUrls);
            saveImage(mergedImage, "merged_image.jpg");
            System.out.println("Images merged successfully.");
        } catch (IOException e) {
            System.out.println("Failed to merge images: " + e.getMessage());
        }
    }

    public static BufferedImage mergeImages(String[] imageUrls) throws IOException {
        BufferedImage[] images = new BufferedImage[imageUrls.length];

        // 加载图片
        for (int i = 0; i < imageUrls.length; i++) {
            URL url = new URL(imageUrls[i]);
            images[i] = ImageIO.read(url);
        }

        // 计算合并后图片的宽度和高度
        int maxWidth = 0;
        int totalHeight = 0;
        for (BufferedImage image : images) {
            maxWidth = Math.max(maxWidth, image.getWidth());
            totalHeight += image.getHeight();
        }

        // 创建合并后的图片
        BufferedImage mergedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = mergedImage.createGraphics();
        int y = 0;
        for (BufferedImage image : images) {
            g2d.drawImage(image, 0, y, null);
            y += image.getHeight();
        }
        g2d.dispose();

        return mergedImage;
    }

    public static void saveImage(BufferedImage image, String fileName) throws IOException {
        File file = new File(fileName);
        ImageIO.write(image, "jpg", file);
    }
}
