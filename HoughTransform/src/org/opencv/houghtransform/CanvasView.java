package org.opencv.houghtransform;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.houghtransform.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class CanvasView extends android.view.View {

	private Bitmap bmpOut, myBitmap;
	private Paint p = new Paint();

	public CanvasView(Context context) {
		super(context);
		Mat mImg = new Mat();
		myBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.test4);
		bmpOut = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(),
				Bitmap.Config.ARGB_8888);
		
		Utils.bitmapToMat(myBitmap, mImg);

		// Convert to gray scale
		Mat mGray = new Mat(mImg.rows(), mImg.cols(), CvType.CV_8UC1);
		Imgproc.cvtColor(mImg, mGray, Imgproc.COLOR_BGRA2GRAY);
		
		Mat mCanny = new Mat(mGray.rows(), mGray.cols(), CvType.CV_8UC1);
		
		// Add Gaussian blur to reduce noise
		Imgproc.GaussianBlur(mGray, mGray, new Size(7, 7), 0, 0);
		
		//Edge detection
		Imgproc.Canny(mGray, mCanny, 125d, 250d, 3, false);

		// Obtain an array with circles using Hough Transform algorithm
		Mat circleImage = new Mat(mGray.rows(), mGray.cols(), CvType.CV_8UC1);
		Imgproc.HoughCircles(mGray, circleImage, Imgproc.CV_HOUGH_GRADIENT, 1d,
				(double) mGray.height() / 3, 250d, 100d, 10, 400);

		if (circleImage.rows() == 1) {
			System.out.println("circleImage.cols(): " + circleImage.cols());
			for (int i = 0; i < circleImage.cols(); i++) {
				Core.circle(mImg, new Point(circleImage.get(0, i)[0],
						circleImage.get(0, i)[1]),
						(int) circleImage.get(0, i)[2], new Scalar(255d, 0d,
								0d), 2);
			}
		}

		// Convert back to a bitmap suitable for drawing
		Utils.matToBitmap(mImg, bmpOut);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Render the output
		canvas.drawBitmap(bmpOut, 0, 0, p);

	}

}