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
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
		Utils.bitmapToMat(myBitmap, mImg);
		Mat mGray = new Mat(mImg.rows(), mImg.cols(), CvType.CV_8UC1);
		Imgproc.cvtColor(mImg, mGray, Imgproc.COLOR_BGRA2GRAY);
		Imgproc.GaussianBlur(mGray, mGray, new Size(9, 9), 2, 2);
		Mat circleImage = new Mat(mGray.rows(), mGray.cols(), CvType.CV_8UC1);
		Imgproc.HoughCircles(mGray, circleImage, Imgproc.CV_HOUGH_GRADIENT, 1d,
		         (double) mGray.height() / 70);
		System.out.println(circleImage.dump());
//		System.out.println("check6");
		//newBitmap = null; 
		if (circleImage.rows() == 1){
			for (int i = 0; i < circleImage.cols(); i++){
				//System.out.println(circleImage.get(0, i));
				System.out.println(circleImage.get(0, i)[0] + ", " + circleImage.get(0, i)[1] + ", " + circleImage.get(0, i)[2]);
				Core.circle(mImg, new Point(circleImage.get(0, i)[0], circleImage.get(0, i)[1]), (int) circleImage.get(0, i)[2], new Scalar(1.0, 0.0, 0.0, 1.0), 5);
			}
		}
		 bmpOut = 
				 Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), 
				 Bitmap.Config.ARGB_8888); 
//		 System.out.println(circleImage.cols() + " " + circleImage.rows());
		 Utils.matToBitmap(mImg, bmpOut);
		//Utils.matToBitmap(circleImage, bmpOut);
		System.out.println("check8");
		// TODO Auto-generated constructor stub

	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//File imgFile = new File(Environment.getExternalStorageDirectory() + "/test.jpg");
		//System.out.println(Environment.getExternalStorageDirectory());
		//Resources r = getResources();
		//System.out.println(r.toString());
		//R.drawable.map;
//		canvas.drawColor(Color.WHITE);
//		Paint paint = new Paint();
//		paint.setStyle(Paint.Style.FILL);
//		canvas.drawCircle(20, 32, 20, paint);
		System.out.println("checkDraw");
        canvas.drawBitmap(bmpOut, 0, 0, p);

	}

}