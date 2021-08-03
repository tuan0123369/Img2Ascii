
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

public class Img2Ascii {

    private BufferedImage img;
    private double pixval;
    private PrintWriter prntwrt;
    private FileWriter filewrt;

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public BufferedImage resize(BufferedImage img, int scaledWidth, int scaledHeight) {
        BufferedImage outImg = new BufferedImage(scaledWidth, scaledHeight, img.getType());
        Graphics2D g2d = outImg.createGraphics();
        g2d.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return outImg;
    }

    public Img2Ascii() {
        try {
            prntwrt = new PrintWriter(filewrt = new FileWriter("asciiart.txt", true));
        } catch (IOException ex) {
        }
    }

    /**
     *
     * @param imgname: name or absolute path of image
     * @param widthChar: width of Ascii image
     * @param hightChar: hight of Ascii image
     */
    public void convertToAscii(String imgname, int widthChar, int hightChar) {
        try {
            img = ImageIO.read(new File(imgname));
        } catch (IOException e) {
        }
        BufferedImage tempImg = resize(img, widthChar, hightChar);
        for (int i = 0; i < tempImg.getHeight(); i++) {
            for (int j = 0; j < tempImg.getWidth(); j++) {
                Color pixcol = new Color(tempImg.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol.getGreen() * 0.11)));
                String x = strChar(pixval);
                print(x);
                System.out.print(x);
            }
            System.out.println();
            try {
                prntwrt.println("");
                prntwrt.flush();
                filewrt.flush();
            } catch (IOException ex) {
            }
        }
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }

    public void print(String str) {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            filewrt.flush();
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        Img2Ascii obj = new Img2Ascii();
        obj.convertToAscii("image.png", 200, 65);
    }
}
