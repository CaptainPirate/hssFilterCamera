package googlog.com.hsscamerafilterlibrary.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;

import googlog.com.hsscamerafilterlibrary.camera.utils.CameraUtils;

public class CameraEngineInterface {
    private static String TAG = "CameraEngineInterFace";
    private  Camera camera = null;
    private  int cameraID = 0;
    private  SurfaceTexture surfaceTexture;
    private  SurfaceView surfaceView;
    private  Parameters parameters;
    private static CameraEngineInterface mCameraEngine;

    private CameraEngineInterface(){

    }
    public static synchronized CameraEngineInterface getInstance(){
        if(mCameraEngine == null){
            mCameraEngine = new CameraEngineInterface();
        }
        return mCameraEngine;
    }
    public  Camera getCamera(){
        return camera;
    }

    public  boolean openCamera(){
        if(camera == null){
            try{
                camera = Camera.open(cameraID);
                setDefaultParameters();
       // camera.setDisplayOrientation(270);
                return true;
            }catch(RuntimeException e){
                return false;
            }
        }
        return false;
    }

    public  boolean openCamera(int id){
        if(camera == null){
            try{
                camera = Camera.open(id);
                cameraID = id;
                setDefaultParameters();
        //camera.setDisplayOrientation(270);
                return true;
            }catch(RuntimeException e){
                return false;
            }
        }
        return false;
    }

    public  int getCameraID(){
        return cameraID;
    }

    public  void releaseCamera(){
        if(camera != null){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void resumeCamera(){
        openCamera();
    }

    public  void setParameters(Parameters parameters){
        camera.setParameters(parameters);
    }

    public  Parameters getParameters(){
        if(camera != null)
            camera.getParameters();
        return null;
    }

    public  void switchCamera(){
        releaseCamera();
        cameraID = cameraID == 0 ? 1 : 0;
        openCamera(cameraID);
        startPreview(surfaceTexture);
    }

     public void setCameraDisplayOrientation() {
          Camera.CameraInfo info = new Camera.CameraInfo();
          Camera.getCameraInfo(cameraID, info);
          int result=0;
          Log.d(TAG,"info.orientation = "+info.orientation);
          if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
          Log.d(TAG,"info.facing = "+info.facing);
              if(info.orientation==90){//qian_amaza
                   result = (360 - result) % 360;  // compensate the mirror
	      }else if(info.orientation==270){//qian_lg
                   result = (360 + result) % 360;  // compensate the mirror
	      }
             // result = info.orientation;
              Log.d(TAG,"result1 = "+result);

          } else {  // back-facing
              if(info.orientation==90){
              result = (info.orientation + 360-90) % 360;
              }else if(info.orientation==270){
              result = (info.orientation + 360-270) % 360;
              }
              Log.d(TAG,"result2 = "+result);
          }
          camera.setDisplayOrientation(result);
      }

    private  void setDefaultParameters(){
        parameters = camera.getParameters();
        if (parameters.getSupportedFocusModes().contains(
                Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        Size previewSize = CameraUtils.getLargePreviewSize(camera);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        Size pictureSize = CameraUtils.getLargePictureSize(camera);
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        //parameters.setRotation(90);
        setCameraDisplayOrientation();
        camera.setParameters(parameters);
    }

    public  synchronized void setNewParameters(int ratio){
        parameters = camera.getParameters();
        Size previewSize;
        Size pictureSize;
        if (parameters.getSupportedFocusModes().contains(
                Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        if(ratio==43){
		previewSize = CameraUtils.getLargePreviewSize(camera);
		parameters.setPreviewSize(previewSize.width, previewSize.height);
		pictureSize = CameraUtils.getLargePictureSize(camera);
		parameters.setPictureSize(pictureSize.width, pictureSize.height);
        setCameraDisplayOrientation();
		//parameters.setRotation(90);
		camera.setParameters(parameters);
        }else{
		previewSize = CameraUtils.getLargePreviewSize_16_9(camera);
		parameters.setPreviewSize(previewSize.width, previewSize.height);
		pictureSize = CameraUtils.getLargePictureSize_16_9(camera);
		parameters.setPictureSize(pictureSize.width, pictureSize.height);
		//parameters.setRotation(90);
        setCameraDisplayOrientation();
		camera.setParameters(parameters);
        }
    }

    public  Size getPreviewSize(){
        /*if(camera==null){
            openCamera();
        }*/
        return camera.getParameters().getPreviewSize();
    }

    public  Size getPictureSize(){
        return camera.getParameters().getPictureSize();
    }

    public  void startPreview(SurfaceTexture surfaceTexture){
        if(camera != null)
            try {
                camera.setPreviewTexture(surfaceTexture);
                this.surfaceTexture = surfaceTexture;
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public  void startPreview(){
        if(camera != null)
            camera.startPreview();
    }

    public  void stopPreview(){
        camera.stopPreview();
    }

    public  void setRotation(int rotation){
        Parameters params = camera.getParameters();
        params.setRotation(rotation);
        camera.setParameters(params);
    }

    public  void takePicture(Camera.ShutterCallback shutterCallback, Camera.PictureCallback rawCallback,
                                   Camera.PictureCallback jpegCallback){
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    public  googlog.com.hsscamerafilterlibrary.camera.utils.CameraInfo getCameraInfo(){
        googlog.com.hsscamerafilterlibrary.camera.utils.CameraInfo info = new googlog.com.hsscamerafilterlibrary.camera.utils.CameraInfo();
        Size size = getPreviewSize();
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraID, cameraInfo);
        info.previewWidth = size.width;
        info.previewHeight = size.height;
        info.orientation = cameraInfo.orientation;
        info.isFront = cameraID == 1 ? true : false;
        size = getPictureSize();
        info.pictureWidth = size.width;
        info.pictureHeight = size.height;
        return info;
    }
}
