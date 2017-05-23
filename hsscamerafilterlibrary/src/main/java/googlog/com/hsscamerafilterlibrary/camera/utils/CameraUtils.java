package googlog.com.hsscamerafilterlibrary.camera.utils;

import android.hardware.Camera;

import java.util.List;
import android.util.Log;

/**
 * Created by hss on 2016/2/25.
 */
public class CameraUtils {

    public static Camera.Size getLargePictureSize(Camera camera){//4:3
        if(camera != null){
            List<Camera.Size> sizes = camera.getParameters().getSupportedPictureSizes();
            Camera.Size temp = sizes.get(0);
            for(int i = 1;i < sizes.size();i ++){
                Log.d("88hss"+i,"sizes.get(+"+i+").height= "+sizes.get(i).height+" sizes.get(+"+i+").width= "+sizes.get(i).width);
                float scale = (float)(sizes.get(i).height) / sizes.get(i).width;
                //if(temp.width < sizes.get(i).width && scale < 0.6f && scale > 0.5f)
                  if(temp.width < sizes.get(i).width && scale > 0.7f)
                    temp = sizes.get(i);
            }
            return temp;
        }
        return null;
    }

    public static Camera.Size getLargePreviewSize(Camera camera){//4:3
        if(camera != null){
            List<Camera.Size> sizes = camera.getParameters().getSupportedPreviewSizes();
            Camera.Size temp = sizes.get(0);
            for(int i = 1;i < sizes.size();i ++){
                 float scale = (float)(sizes.get(i).height) / sizes.get(i).width;
                Log.d("88hss66"+i,"sizes.get(+"+i+").height= "+sizes.get(i).height+" sizes.get(+"+i+").width= "+sizes.get(i).width);
                if(temp.width < sizes.get(i).width && scale > 0.7f)
                    temp = sizes.get(i);
            }
            return temp;
        }
        return null;
    }

    public static Camera.Size getLargePictureSize_16_9(Camera camera){//16:9
        if(camera != null){
            List<Camera.Size> sizes = camera.getParameters().getSupportedPictureSizes();
            Camera.Size temp = sizes.get(0);
            for(int i = 1;i < sizes.size();i ++){
                Log.d("88hss"+i,"sizes.get(+"+i+").height= "+sizes.get(i).height+" sizes.get(+"+i+").width= "+sizes.get(i).width);
                float scale = (float)(sizes.get(i).height) / sizes.get(i).width;
                if(temp.width < sizes.get(i).width && scale < 0.6f && scale > 0.5f)
                  //if(temp.width < sizes.get(i).width && scale > 0.7f)
                    temp = sizes.get(i);
            }
            return temp;
        }
        return null;
    }

    public static Camera.Size getLargePreviewSize_16_9(Camera camera){//16:9
        if(camera != null){
            List<Camera.Size> sizes = camera.getParameters().getSupportedPreviewSizes();
            Camera.Size temp = sizes.get(0);
            for(int i = 1;i < sizes.size();i ++){
                 float scale = (float)(sizes.get(i).height) / sizes.get(i).width;
                Log.d("88hss66"+i,"sizes.get(+"+i+").height= "+sizes.get(i).height+" sizes.get(+"+i+").width= "+sizes.get(i).width);
                if(temp.width < sizes.get(i).width &&  scale < 0.6f && scale > 0.5f)
                    temp = sizes.get(i);
            }
            return temp;
        }
        return null;
    }
}
