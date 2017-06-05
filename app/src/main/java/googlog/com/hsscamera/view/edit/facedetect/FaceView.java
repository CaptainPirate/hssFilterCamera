package googlog.com.hsscamera.view.edit.facedetect;

import googlog.com.hsscamera.R;
import googlog.com.hsscamerafilterlibrary.camera.CameraEngineInterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Face;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class FaceView extends ImageView {
	private static final String TAG = "FaceView";
	private Context mContext;
	private Paint mLinePaint;
	private Face[] mFaces;
	private Matrix mMatrix = new Matrix();
	private RectF mRect = new RectF();
//	private Point leftEyePoint = new Point();
//	private Point rightEyePoint = new Point();
//	private Point mouthPoint = new Point();
	private Drawable mFaceIndicator = null;
	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initPaint();
		mContext = context;
		mFaceIndicator = getResources().getDrawable(R.drawable.ic_face_find_2);
	}


	public void setFaces(Face[] faces){
		this.mFaces = faces;
		invalidate();
	}
	public void clearFaces(){
		mFaces = null;
		invalidate();
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(mFaces == null || mFaces.length < 1){
			Log.d(TAG,"null mFaces");
			return;
		}
		//FaceDetector face ;
		boolean isMirror = false;
		int Id = CameraEngineInterface.getInstance().getCameraID();
		if(Id == CameraInfo.CAMERA_FACING_BACK){
			isMirror = false; //后置Camera无需mirror
		}else if(Id == CameraInfo.CAMERA_FACING_FRONT){
			isMirror = true;  //前置Camera需要mirror
		}
		prepareMatrix(mMatrix, isMirror, 90, getWidth(), getHeight());
		canvas.save();
		mMatrix.postRotate(0); //Matrix.postRotate默认是顺时针
		canvas.rotate(-0);   //Canvas.rotate()默认是逆时针 
		for(int i = 0; i< mFaces.length; i++){
			//-------hss add for eye start ----------
			//int x = mFaces[i].leftEye.x;
			//int y =
			Point leftEyePoint = mFaces[i].leftEye;//左眼
			//rightEyePoint = mFaces[i].rightEye;//右眼
			//mouthPoint = mFaces[i].mouth;//嘴巴
			if(leftEyePoint!=null) {
				Paint p = new Paint();
				p.setAntiAlias(true);
				p.setStrokeWidth(6);
				p.setStyle(Style.STROKE);
				p.setColor(Color.BLUE);
				canvas.drawCircle(leftEyePoint.x, leftEyePoint.y, 50, p);
			}
			//-------hss add for eye start ----------
			mRect.set(mFaces[i].rect);
			mMatrix.mapRect(mRect);
            mFaceIndicator.setBounds(Math.round(mRect.left), Math.round(mRect.top),
                    Math.round(mRect.right), Math.round(mRect.bottom));

            mFaceIndicator.draw(canvas);
//			canvas.drawRect(mRect, mLinePaint);
		}
		canvas.restore();
		super.onDraw(canvas);
	}

	public void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
									 int viewWidth, int viewHeight) {
		// Need mirror for front camera.
		matrix.setScale(mirror ? -1 : 1, 1);
		// This is the value for android.hardware.Camera.setDisplayOrientation.
		matrix.postRotate(displayOrientation);
		// Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
		// UI coordinates range from (0, 0) to (width, height).
		matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
		matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
	}

	private void initPaint(){
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		int color = Color.rgb(0, 150, 255);
		int color = Color.rgb(98, 212, 68);
//		mLinePaint.setColor(Color.RED);
		mLinePaint.setColor(color);
		mLinePaint.setStyle(Style.STROKE);
		mLinePaint.setStrokeWidth(5f);
		mLinePaint.setAlpha(180);
	}
}
