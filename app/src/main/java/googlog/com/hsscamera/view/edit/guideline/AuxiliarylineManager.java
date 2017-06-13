
package googlog.com.hsscamera.view.edit.guideline;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import googlog.com.hsscamera.R;
import googlog.com.hsscamera.activity.CameraActivity;

public class AuxiliarylineManager extends ViewManager {
    private static final String TAG = "AuxiliarylineManager"; 

    private ImageView mAuxiliarylineImage;

    private boolean mIsAuxiliarylineShow = false;

    public AuxiliarylineManager(CameraActivity context) {
        super(context, VIEW_LAYER_BOTTOM);

    }

    @Override
    protected View getView() {
        View view = null;
        view = inflate(R.layout.pl_camera_controls_auxiliary_line_layout);
		mAuxiliarylineImage = (ImageView)view.findViewById(R.id.camera_controls_auxiliary_line_image);
        return view;
    }

    public void showAuxiliaryline(int id){
	show();
	if(mAuxiliarylineImage != null){
		mAuxiliarylineImage.setImageResource(id);
		mAuxiliarylineImage.setVisibility(View.VISIBLE);
		mIsAuxiliarylineShow = true;
	}
    }
    public void show(){
	super.show();
    }
    public void hideAuxiliaryline(){
	if(mAuxiliarylineImage != null){
		mAuxiliarylineImage.setVisibility(View.INVISIBLE);
	}
	mIsAuxiliarylineShow = false;
	hide();
    }
    public void hide(){
	super.hide();
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

}
