package as;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Computes the Mandelbrot set.
 * http://en.wikipedia.org/wiki/Mandelbrot_set
 */
public class Mandelbrot {
	public static final int IMAGE_LENGTH = 1024;
	public static final int MAX_ITERATIONS = 512;
	
	public static final int COLOR_COUNT = 64;
	private static Color[] colors = generateColors(COLOR_COUNT);
	
	private static Color getColor(int iterations) {
		return iterations == MAX_ITERATIONS ? Color.BLACK : colors[iterations % COLOR_COUNT];
	}

	private static Color[] generateColors(int n) {
		Color[] cols = new Color[n];
		for (int i = 0; i < n; i++) {
			cols[i] = Color.hsb(((float) i / (float) n) * 360, 0.85f, 1.0f);
		}
		return cols;
	}

	public static void computeSequential(PixelPainter painter, Plane plane, CancelSupport cancel) {
		double half = plane.length / 2;
		double reMin = plane.center.r - half;
		double imMax = plane.center.i + half;
		double step = plane.length / IMAGE_LENGTH;

		for (int x = 0; x < IMAGE_LENGTH && !cancel.isCancelled(); x++) { // x-axis
			double re = reMin + x * step; // map pixel to complex plane
			for (int y = 0; y < IMAGE_LENGTH; y++) { // y-axis
				double im = imMax - y * step; // map pixel to complex plane

				int iterations = mandel(re, im);
				painter.paint(x, y, getColor(iterations));
			}
		}
	}

	
	public static void computeParallel(PixelPainter painter, Plane plane, CancelSupport cancel) {
        int threadCount = Runtime.getRuntime().availableProcessors() * 2;
        threadCount = 64;
        int l = IMAGE_LENGTH / threadCount;
        List<Thread> threadList = new LinkedList<>();
        for (int x = 0; x < threadCount && !cancel.isCancelled(); x++) { // x-axis
            final int startStep = (l*x);
            final int endStep = (l*(x+1));
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    double half = plane.length / 2;
                    double reMin = plane.center.r - half;
                    double imMax = plane.center.i + half;
                    double step = plane.length / IMAGE_LENGTH;
                    for (int x = startStep; x < endStep && !cancel.isCancelled(); x++) {
                        double re = reMin + x * step; // map pixel to complex plane
                        for (int y = 0; y < IMAGE_LENGTH; y++) { // y-axis
                            double im = imMax - y * step; // map pixel to complex plane

                            int iterations = mandel(re, im);
                            painter.paint(x, y, getColor(iterations));
                        }
                    }
                }
            });
            threadList.add(t);
            t.start();
        }
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

	/**
	 * z_n+1 = z_n^2 + c starting with z_0 = 0
	 * 
	 * Checks whether c = re + i*im is a member of the Mandelbrot set.
	 * 
	 * @param cre
	 *            real part
	 * @param cim
	 *            imaginary part
	 * @return the number of iterations
	 */
	public static final int mandel(double cre, double cim) {
		double re = 0.0;
		double im = 0.0;
		int iterations = 0;
		while (re * re + im * im <= 4 && iterations < MAX_ITERATIONS) {
			double re1 = re * re - im * im + cre;
			double im1 = 2 * re * im + cim;
			re = re1;
			im = im1;
			iterations++;
		}
		return iterations;
	}


//  Nice implementation for experiments with different iteration formulas but far too slow
//	public static int mandel(double re, double im) {
//		Complex c = new Complex(re, im);
//		Complex z = Complex.ZERO;
//		int iterations = 0;
//		while (z.absSq() <= 4 && iterations < MAX_ITERATIONS) {
//			z = z.pow(2).plus(c);
//			iterations++;
//		}
//		return iterations;
//	}
}
