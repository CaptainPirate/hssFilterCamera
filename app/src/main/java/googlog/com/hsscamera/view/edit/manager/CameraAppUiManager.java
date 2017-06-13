package googlog.com.hsscamera.view.edit.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileDescriptor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import googlog.com.hsscamera.R;
import googlog.com.hsscamera.activity.CameraActivity;
import googlog.com.hsscamera.view.edit.guideline.AuxiliarylineManager;
import googlog.com.hsscamera.view.edit.guideline.ViewManager;
import googlog.com.hsscamera.view.edit.platform.ICameraAppUi;
import googlog.com.hsscamera.view.edit.platform.ICameraView;

/**
 * Created by ubuntu on 17-6-8.
 */

public class CameraAppUiManager implements ICameraAppUi{

    private static String TAG = "CameraAppUiManager";
    private final CameraActivity mCameraActivity;
    private AuxiliarylineManager mAuxiliarylineManager;
    private ViewGroup mViewLayerBottom;
    private ViewGroup mViewLayerNormal;
    private MainHandler mMainHandler;
    private List<ViewManager> mViewManagers = new CopyOnWriteArrayList<ViewManager>();

    private final class MainHandler extends Handler {

        public MainHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "msg id=" + msg.what);
            switch (msg.what) {

                default:
                    break;
            }
        }
    }


    public CameraAppUiManager(CameraActivity context) {
        Log.i(TAG, "[CameraAppUiImpl] constructor... ");
        mCameraActivity = context;
        mMainHandler = new MainHandler(context.getMainLooper());
    }

    public void createCommonView() {

        mAuxiliarylineManager = new AuxiliarylineManager(mCameraActivity);
    }

    @Override
    public ICameraView getCameraView(CommonUiType type) {
        return null;
    }

    @Override
    public ICameraView getCameraView(SpecViewType mode) {
        return null;
    }

    @Override
    public void setReviewListener(View.OnClickListener retatekListener, View.OnClickListener playListener) {

    }

    @Override
    public void showReview(String path, FileDescriptor fd) {

    }

    @Override
    public void hideReview() {

    }

    @Override
    public void setReviewCompensation(int compensation) {

    }

    @Override
    public AuxiliarylineManager getAuxiliarylineManager() {
        return mAuxiliarylineManager;
    }

    @Override
    public ViewGroup getBottomViewLayer() {
        return mViewLayerBottom;
    }

    @Override
    public ViewGroup getNormalViewLayer() {
        return mViewLayerNormal;
    }

    public boolean addViewManager(ViewManager viewManager) {
        if (!mViewManagers.contains(viewManager)) {
            return mViewManagers.add(viewManager);
        }
        return false;
    }

    public boolean removeViewManager(ViewManager viewManager) {
        return mViewManagers.remove(viewManager);
    }
    public void addView(View view, int layer) {
        ViewGroup group = getViewLayer(layer);
        if (group != null) {
            group.addView(view);
        }
    }

    public void removeView(View view, int layer) {
        ViewGroup group = getViewLayer(layer);
        if (group != null) {
            group.removeView(view);
        }
    }

    public void initializeViewGroup() {
        mViewLayerBottom = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_bottom);
        mViewLayerNormal = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_normal);
//        mViewLayerTop = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_top);
//        mViewLayerShutter = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_shutter);
//        mViewLayerSetting = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_setting);
//        mViewLayerOverlay = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_overlay);
//        mViewLayerModePicker = (ViewGroup) mCameraActivity.findViewById(R.id.view_layer_mode_picker);
    }

    public void removeAllView() {
        if (mViewLayerBottom != null) {
            mViewLayerBottom.removeAllViews();
        }
        if (mViewLayerNormal != null) {
            mViewLayerNormal.removeAllViews();
        }
//        if (mViewLayerShutter != null) {
//            mViewLayerShutter.removeAllViews();
//        }
//        if (mViewLayerSetting != null) {
//            mViewLayerSetting.removeAllViews();
//        }
//        if (mViewLayerOverlay != null) {
//            mViewLayerOverlay.removeAllViews();
//        }
    }

    public View inflate(int layoutId, int layer) {
        // mViewLayerNormal, mViewLayerBottom and mViewLayerTop are same
        // ViewGroup.
        // Here just use one to inflate child view.
        return mCameraActivity.getLayoutInflater().inflate(layoutId, getViewLayer(layer), false);
    }

    private ViewGroup getViewLayer(int layer) {
        Log.i(TAG, "[getViewLayer] layer:" + layer);
        ViewGroup viewLayer = null;
        switch (layer) {
            case ViewManager.VIEW_LAYER_BOTTOM:
                viewLayer = mViewLayerBottom;
                break;
            case ViewManager.VIEW_LAYER_NORMAL:
                viewLayer = mViewLayerNormal;
                break;
            case ViewManager.VIEW_LAYER_TOP:
                //viewLayer = mViewLayerTop;
                break;
            case ViewManager.VIEW_LAYER_SHUTTER:
                //viewLayer = mViewLayerShutter;
                break;
            case ViewManager.VIEW_LAYER_SETTING:
                //viewLayer = mViewLayerSetting;
                break;
            case ViewManager.VIEW_LAYER_OVERLAY:
               // viewLayer = mViewLayerOverlay;
                break;
            case ViewManager.VIEW_LAYER_MODE_PICKER:
               // viewLayer = mViewLayerModePicker;
                break;

            default:
                throw new RuntimeException("Wrong layer:" + layer);
        }
        return viewLayer;
    }

}
