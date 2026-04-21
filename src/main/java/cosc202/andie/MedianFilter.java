/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 *
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 *
 * <p>
 * A Median filter that takes the median of a neighbourhood of values around
 * each pixel as indicated by the radius and replaces that pixel.
 * </p>
 *
 * @author parma754
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2
     * a 5x5 filter, and so forth.
     */
    private int radius;
    private int numThreads; 

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used. A
     * size of 1 is a 3x3 filter, 2 is 5x5, and so on. Larger filters give a
     * stronger blurring effect.
     * </p>
     *
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius, int numThreads) {
        this.radius = radius;
        this.numThreads = numThreads; 
    }

    /**
     * Constructs median filter with default radius of 1
     */
    MedianFilter() {
        this(1, Runtime.getRuntime().availableProcessors());
        
    }

    /**
     * Applies the median filer to an image.
     *
     * <p>
     * For each pixel, a square neighbourhood indicated by the radius is
     * considered. Each color channel is collected, sorted and the median value
     * computed. The median values construct the new pixel.
     * </p>
     *
     *
     * @param input the input {@link BufferedImage} to be filtered
     * @return a new {@link BufferedImage} that has the median filter applied
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);

        // multithreading to fix lagging issue
       // int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = height / numThreads;

        for (int t = 0; t < numThreads; t++) {
            final int startRow = t * rowsPerThread;
            final int endRow = (t == numThreads - 1) ? height : startRow + rowsPerThread;
//this gonna make it really fast.. trusttttt 
            threads[t] = new Thread(() -> {
                for (int y = startRow; y < endRow; y++) {
                    for (int x = 0; x < width; x++) {
                   
                        List<Integer> rValues = new ArrayList<>();
                        List<Integer> gValues = new ArrayList<>();
                        List<Integer> bValues = new ArrayList<>();
                        List<Integer> aValues = new ArrayList<>();
                        for (int ky = -radius; ky <= radius; ky++) {
                            for (int kx = -radius; kx <= radius; kx++) {
                                int nx = Math.max(0, Math.min(x + kx, width - 1));
                                int ny = Math.max(0, Math.min(y + ky, height - 1));
                                int argb = input.getRGB(nx, ny);
                                rValues.add((argb >> 16) & 0xFF);
                                gValues.add((argb >> 8) & 0xFF);
                                bValues.add(argb & 0xFF);
                                aValues.add((argb >> 24) & 0xFF);
                            }
                        }
                        Collections.sort(rValues);
                        Collections.sort(gValues);
                        Collections.sort(bValues);
                        Collections.sort(aValues);
                        int mid = rValues.size() / 2;
                        int argb = (aValues.get(mid) << 24) | (rValues.get(mid) << 16) | (gValues.get(mid) << 8) | bValues.get(mid);
                        output.setRGB(x, y, argb);
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}
