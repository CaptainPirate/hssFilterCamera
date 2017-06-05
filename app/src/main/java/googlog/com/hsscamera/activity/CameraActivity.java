package googlog.com.hsscamera.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import googlog.com.hsscamera.R;
import googlog.com.hsscamera.adapter.FilterAdapter;
import googlog.com.hsscamera.view.edit.facedetect.FaceView;
import googlog.com.hsscamera.view.edit.facedetect.GoogleFaceDetect;
import googlog.com.hsscamera.widget.PreviewFrameLayout;
import googlog.com.hsscamerafilterlibrary.MagicEngine;
import googlog.com.hsscamerafilterlibrary.camera.CameraEngineInterface;
import googlog.com.hsscamerafilterlibrary.filter.helper.MagicFilterType;
import googlog.com.hsscamerafilterlibrary.utils.MagicParams;
import googlog.com.hsscamerafilterlibrary.widget.MagicCameraView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.widget.FrameLayout;


/**
 * Created by hss on 2016/3/17.
 */
public class CameraActivity extends Activity implements
        PreviewFrameLayout.OnSizeChangedListener{
    private static String TAG = "CameraActivity";
    private LinearLayout mFilterLayout;
    private LinearLayout mRatioLayout;
    private RecyclerView mFilterListView;
    private FilterAdapter mAdapter;
    private FrameLayout mPreviewFrameLayout;
    private MagicEngine magicEngine;
    private boolean isRecording = false;
    private final int MODE_PIC = 1;
    private final int MODE_VIDEO = 2;
    public static final int CAMERA_HAS_STARTED_PREVIEW = 1;
    public static final int UPDATE_FACE_RECT = 0;
    private int mode = MODE_PIC;

    private FaceView mFaceView;
    private ImageView btn_shutter;
    private ImageView btn_mode;
    private ImageView btn_camera_ratio;

    private TextView ratio_4_3;
    private TextView ratio_16_9;
    private ObjectAnimator animator;

    private int mPreviewFrameWidth;
    private int mPreviewFrameHeight;
    private int mUnCropWidth;
    private int mUnCropHeight;
    private MainHandler mMainHandler = null;
    GoogleFaceDetect mfacedetect = null;
    MagicCameraView cameraView;

    private final MagicFilterType[] types = new MagicFilterType[]{
            MagicFilterType.NONE,
            MagicFilterType.FAIRYTALE,
            MagicFilterType.SUNRISE,
            MagicFilterType.SUNSET,
            MagicFilterType.WHITECAT,
            MagicFilterType.BLACKCAT,
            MagicFilterType.SKINWHITEN,
            MagicFilterType.HEALTHY,
            MagicFilterType.SWEETS,
            MagicFilterType.ROMANCE,
            MagicFilterType.SAKURA,
            MagicFilterType.WARM,
            MagicFilterType.ANTIQUE,
            MagicFilterType.NOSTALGIA,
            MagicFilterType.CALM,
            MagicFilterType.LATTE,
            MagicFilterType.TENDER,
            MagicFilterType.COOL,
            MagicFilterType.EMERALD,
            MagicFilterType.EVERGREEN,
            MagicFilterType.CRAYON,
            MagicFilterType.SKETCH,
            MagicFilterType.AMARO,
            MagicFilterType.BRANNAN,
            MagicFilterType.BROOKLYN,
            MagicFilterType.EARLYBIRD,
            MagicFilterType.FREUD,
            MagicFilterType.HEFE,
            MagicFilterType.HUDSON,
            MagicFilterType.INKWELL,
            MagicFilterType.KEVIN,
            MagicFilterType.LOMO,
            MagicFilterType.N1977,
            MagicFilterType.NASHVILLE,
            MagicFilterType.PIXAR,
            MagicFilterType.RISE,
            MagicFilterType.SIERRA,
            MagicFilterType.SUTRO,
            MagicFilterType.TOASTER2,
            MagicFilterType.VALENCIA,
            MagicFilterType.WALDEN,
            MagicFilterType.XPROII
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_camera);
        MagicEngine.Builder builder = new MagicEngine.Builder();
        magicEngine = builder
                .build((MagicCameraView)findViewById(R.id.glsurfaceview_camera));
        initView();
        mMainHandler = new MainHandler();
        mfacedetect = new GoogleFaceDetect(getApplicationContext(),mMainHandler);
        mMainHandler.sendEmptyMessageDelayed(CAMERA_HAS_STARTED_PREVIEW, 1000);
    }

    private void initView(){
        mPreviewFrameLayout = (FrameLayout) findViewById(R.id.previewframe);
        mFilterLayout = (LinearLayout)findViewById(R.id.layout_filter);
        mRatioLayout = (LinearLayout)findViewById(R.id.layout_ratio);
        mFilterListView = (RecyclerView) findViewById(R.id.filter_listView);

        btn_shutter = (ImageView)findViewById(R.id.btn_camera_shutter);
        btn_mode = (ImageView)findViewById(R.id.btn_camera_mode);
        mFaceView = (FaceView) findViewById(R.id.face_view);
        cameraView = (MagicCameraView)findViewById(R.id.glsurfaceview_camera);

        findViewById(R.id.btn_camera_filter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_closefilter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_shutter).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_switch).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_mode).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_beauty).setOnClickListener(btn_listener);
        findViewById(R.id.btn_camera_ratio).setOnClickListener(btn_listener);   
        findViewById(R.id.ratio_4_3).setOnClickListener(btn_listener); 
        findViewById(R.id.ratio_16_9).setOnClickListener(btn_listener); 
        findViewById(R.id.btn_camera_closeratio).setOnClickListener(btn_listener);
        
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFilterListView.setLayoutManager(linearLayoutManager);

        mAdapter = new FilterAdapter(this, types);
        mFilterListView.setAdapter(mAdapter);
        mAdapter.setOnFilterChangeListener(onFilterChangeListener);

        animator = ObjectAnimator.ofFloat(btn_shutter,"rotation",0,360);
        animator.setDuration(500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
      /*  Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();
        params.width = screenSize.x;
        params.height = screenSize.x * 4 / 3;
        cameraView.setLayoutParams(params);
        mPreviewFrameLayout.setAspectRatio((double)4/3);*/
        //mPreviewFrameLayout.setOnSizeChangedListener(this);
    }

    @Override
    public void onSizeChanged(int width, int height){
        if ((CameraEngineInterface.getInstance() != null)&&(CameraEngineInterface.getInstance().getCamera() != null) && (CameraEngineInterface.getInstance().getPreviewSize() != null)) {
            Camera.Size size = CameraEngineInterface.getInstance().getPreviewSize();
            int w = size.width;
            int h = size.height;
            double mAspectRatio = (double) w / h;
            if (width > height) {
                mUnCropWidth = Math.max(width, (int) (height * mAspectRatio));
                mUnCropHeight = Math.max(height, (int) (width / mAspectRatio));
            } else {
                mUnCropWidth = Math.max(width, (int) (height / mAspectRatio));
                mUnCropHeight = Math.max(height, (int) (width * mAspectRatio));
            }
        } else {
            // 4:3 mode,parameter is null, then will invoke the following code
            mUnCropWidth = width;
            mUnCropHeight = height;
        }

//        if (mFocusManager != null) {
//            mFocusManager.setPreviewSize(mUnCropWidth, mUnCropHeight);
//            mFocusManager.setCropPreviewSize(width, height);
//        }

        mPreviewFrameWidth = width;
        mPreviewFrameHeight = height;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();
        params.width = mPreviewFrameWidth;
        params.height = mPreviewFrameHeight;
       // cameraView.setLayoutParams(params);
    }

    private void setPreviewSize(int ratio){
        CameraEngineInterface.getInstance().setNewParameters(ratio);
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        MagicCameraView cameraView = (MagicCameraView)findViewById(R.id.glsurfaceview_camera);
        FaceView mFaceView = (FaceView) findViewById(R.id.face_view);
       // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();
        FrameLayout.LayoutParams params =  (FrameLayout.LayoutParams) cameraView.getLayoutParams();
        params.width = screenSize.x;
        RelativeLayout.LayoutParams paramsfaceview =  (RelativeLayout.LayoutParams) mFaceView.getLayoutParams();
        params.width = screenSize.x;
        paramsfaceview.width = screenSize.x;
        if(ratio==43){
           params.height = screenSize.x * 4 / 3;
           paramsfaceview.height = screenSize.x * 4 / 3;
          //  mPreviewFrameLayout.setAspectRatio((double)4/3);
        }else if(ratio==169){
           params.height = screenSize.x * 16 / 9;
           paramsfaceview.height = screenSize.x * 4 / 3;
           // mPreviewFrameLayout.setAspectRatio((double)16/9);
        }
        cameraView.setLayoutParams(params);
        mFaceView.setLayoutParams(paramsfaceview);
    }
    private FilterAdapter.onFilterChangeListener onFilterChangeListener = new FilterAdapter.onFilterChangeListener(){

        @Override
        public void onFilterChanged(MagicFilterType filterType) {
            magicEngine.setFilter(filterType);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults.length != 1 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(mode == MODE_PIC)
                takePhoto();
            else
                takeVideo();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private  class MainHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what){
                case UPDATE_FACE_RECT:
                    Face[] faces = (Face[]) msg.obj;
                    mFaceView.setFaces(faces);
                    break;
                case CAMERA_HAS_STARTED_PREVIEW:
                    startGoogleFaceDetect();
                    break;
            }
            super.handleMessage(msg);
        }

    }

    private View.OnClickListener btn_listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_camera_mode:
                    switchMode();
                    break;
                case R.id.btn_camera_shutter:
                    if (PermissionChecker.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                v.getId());
                    } else {
                        if(mode == MODE_PIC)
                            takePhoto();
                        else
                            takeVideo();
                    }
                    break;
                case R.id.btn_camera_filter:
                    showFilters();
                    break;
                case R.id.btn_camera_switch:
                    magicEngine.switchCamera();
                    mMainHandler.sendEmptyMessageDelayed(CAMERA_HAS_STARTED_PREVIEW, 1000);
                    break;
                case R.id.btn_camera_beauty:
                    new AlertDialog.Builder(CameraActivity.this)
                            .setSingleChoiceItems(new String[] { "关闭", "1", "2", "3", "4", "5"}, MagicParams.beautyLevel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        magicEngine.setBeautyLevel(which);
                                        dialog.dismiss();
                                    }
                                })
                            .setNegativeButton("取消", null)
                            .show();
                    break;
                case R.id.btn_camera_ratio:
                     showRatio();//layout_ratio
                    break;
                case R.id.ratio_4_3:
                    setPreviewSize(43);
                    break;
                case R.id.ratio_16_9:
                    setPreviewSize(169);
                    break;
                case R.id.btn_camera_closefilter:
                    hideFilters();
                    break;
                case R.id.btn_camera_closeratio:
                    hideRatios();
                    break;
            }
        }
    };

    private synchronized void startGoogleFaceDetect(){
        Camera.Parameters params = CameraEngineInterface.getInstance().getCamera().getParameters();//getParameters();
        int faceNum = params.getMaxNumDetectedFaces();
        Log.d(TAG,"faceNum = "+faceNum);
        if(faceNum > 0){
            if (mFaceView==null){
                Log.d(TAG,"mFaceView ==null ");
            }
            if(mFaceView != null){
                Log.d(TAG,"mFaceView !=null ");
                mFaceView.clearFaces();
                mFaceView.setVisibility(View.VISIBLE);
            }
            CameraEngineInterface.getInstance().getCamera().setFaceDetectionListener(mfacedetect);
            CameraEngineInterface.getInstance().getCamera().startFaceDetection();
        }
    }
    private void stopGoogleFaceDetect(){
        Camera.Parameters params = CameraEngineInterface.getInstance().getParameters();
        if(params.getMaxNumDetectedFaces() > 0){
            CameraEngineInterface.getInstance().getCamera().setFaceDetectionListener(null);
            CameraEngineInterface.getInstance().getCamera().stopFaceDetection();
            mFaceView.clearFaces();
        }
    }

    private void switchMode(){
        if(mode == MODE_PIC){
            mode = MODE_VIDEO;
            btn_mode.setImageResource(R.drawable.icon_camera);
        }else{
            mode = MODE_PIC;
            btn_mode.setImageResource(R.drawable.icon_video);
        }
    }

    private void takePhoto(){
        magicEngine.savePicture(getOutputMediaFile(),null);
    }

    private void takeVideo(){
        if(isRecording) {
            animator.end();
            magicEngine.stopRecord();
        }else {
            animator.start();
            magicEngine.startRecord();
        }
        isRecording = !isRecording;
    }

    private void showFilters(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", mFilterLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                findViewById(R.id.btn_camera_shutter).setClickable(false);
                mFilterLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    private void showRatio(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRatioLayout, "translationY", mRatioLayout.getHeight(), 0);
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                findViewById(R.id.btn_camera_shutter).setClickable(false);
                mRatioLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    private void hideFilters(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", 0 ,  mFilterLayout.getHeight());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                mFilterLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                mFilterLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }
        });
        animator.start();
    }
    private void hideRatios(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRatioLayout, "translationY", 0 ,  mRatioLayout.getHeight());
        animator.setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                mRatioLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                mRatioLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_camera_shutter).setClickable(true);
            }
        });
        animator.start();
    }
    public File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MagicCamera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}
