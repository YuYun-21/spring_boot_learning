package com.yuyun.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageMerger1 {

    public static void main(String[] args) {
        // 假设有一些图片的URL
        String[] imageUrls = {
                "url_to_image1.jpg",
                "url_to_image2.jpg",
                "url_to_image3.jpg"
        };

        try {
            int maxWidth = 600; // 设置最终图片的最大宽度
            BufferedImage mergedImage = mergeImages(imageUrls, maxWidth);
            saveImage(mergedImage, "merged_image.jpg");
            System.out.println("Images merged successfully.");
        } catch (IOException e) {
            System.out.println("Failed to merge images: " + e.getMessage());
        }
    }

    public static BufferedImage mergeImages(String[] imageUrls, int maxWidth) throws IOException {
        BufferedImage[] images = new BufferedImage[imageUrls.length];

        // 加载图片
        for (int i = 0; i < imageUrls.length; i++) {
            try {
                URL url = new URL(imageUrls[i]);
                images[i] = ImageIO.read(url);
            } catch (IOException e) {
                System.out.println("Failed to load image from URL: " + imageUrls[i]);
                throw e;
            }
        }

        // 计算合并后图片的高度
        int totalHeight = 0;
        int currentWidth = 0;
        int maxHeightInRow = 0; // 用于跟踪当前行的最大高度
        for (BufferedImage image : images) {
            if (currentWidth + image.getWidth() > maxWidth) {
                totalHeight += maxHeightInRow; // 另起一行，将当前行的高度加到总高度中
                currentWidth = 0; // 宽度重置为0
                maxHeightInRow = 0; // 重置当前行的最大高度为0
            }
            currentWidth += image.getWidth();
            maxHeightInRow = Math.max(maxHeightInRow, image.getHeight()); // 更新当前行的最大高度
        }
        totalHeight += maxHeightInRow; // 添加最后一行的高度

        // 创建合并后的图片
        BufferedImage mergedImage = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = mergedImage.createGraphics();
        g2d.setColor(Color.WHITE); // 设置填充颜色为白色
        g2d.fillRect(0, 0, maxWidth, totalHeight); // 填充整个图片
        int x = 0;
        int y = 0;
        for (BufferedImage image : images) {
            if (x + image.getWidth() > maxWidth) {
                // 另起一行
                x = 0;
                y += maxHeightInRow; // 切换到下一行的起始位置
                maxHeightInRow = image.getHeight(); // 重置当前行的最大高度
            }
            g2d.drawImage(image, x, y, null);
            x += image.getWidth();
        }
        g2d.dispose();

        return mergedImage;
    }

    public static void saveImage(BufferedImage image, String fileName) throws IOException {
        File file = new File(fileName);
        ImageIO.write(image, "jpg", file);
    }
}

