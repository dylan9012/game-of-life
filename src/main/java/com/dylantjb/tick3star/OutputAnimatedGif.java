package com.dylantjb.tick3star;

// Tell the compiler where to find the additional classes used in this file

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OutputAnimatedGif {
    private final ImageWriter writer;

    public OutputAnimatedGif(String file) throws IOException {
        FileImageOutputStream output = new FileImageOutputStream(new File(file));
        this.writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
        this.writer.setOutput(output);
        this.writer.prepareWriteSequence(null);
    }

    private BufferedImage makeFrame(boolean[][] world) {
        int width = 40;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(0x242421));
        g.fillRect(0, 0, 200, 200);

        g.setColor(new Color(0x362918));
        // 20x20 squares
        for (int x = 0; x < width + 2; x += 2) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height + 2; y += 2) {
            g.drawLine(0, y, width, y);
        }

        for (int row = 0; row < world.length; row++) {
            for (int col = 0; col < world[row].length; col++) {
                if (world[col][row]) {
                    g.setColor(new Color(0xff7129));
                } else {
                    g.setColor(new Color(0x242421));
                }
                g.fillRect(2 * row + 1, 2 * col + 1, 1, 1);
            }
        }


        g.dispose(); // free up resources used by the graphics context
        return image;
    }

    public void addFrame(boolean[][] world) throws IOException {
        BufferedImage image = makeFrame(world);
        try {
            IIOMetadataNode node = new IIOMetadataNode("javax_imageio_gif_image_1.0");
            IIOMetadataNode extension = new IIOMetadataNode("GraphicControlExtension");
            extension.setAttribute("disposalMethod", "none");
            extension.setAttribute("userInputFlag", "FALSE");
            extension.setAttribute("transparentColorFlag", "FALSE");
            extension.setAttribute("delayTime", "1");
            extension.setAttribute("transparentColorIndex", "255");
            node.appendChild(extension);
            IIOMetadataNode appExtensions = new IIOMetadataNode("ApplicationExtensions");
            IIOMetadataNode appExtension = new IIOMetadataNode("ApplicationExtension");
            appExtension.setAttribute("applicationID", "NETSCAPE");
            appExtension.setAttribute("authenticationCode", "2.0");
            byte[] b = "\u0021\u00ff\u000bNETSCAPE2.0\u0003\u0001\u0000\u0000\u0000".getBytes();
            appExtension.setUserObject(b);
            appExtensions.appendChild(appExtension);
            node.appendChild(appExtensions);
            IIOMetadata metadata;
            metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
            metadata.mergeTree("javax_imageio_gif_image_1.0", node);
            IIOImage t = new IIOImage(image, null, metadata);
            writer.writeToSequence(t, null);
        } catch (IIOInvalidTreeException e) {
            throw new IOException(e);
        }
    }

    public void close() throws IOException {
        writer.endWriteSequence();
    }
}
