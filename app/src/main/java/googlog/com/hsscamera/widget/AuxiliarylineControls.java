package googlog.com.hsscamera.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class AuxiliarylineControls extends CameraControls {

    private Context mContext;

    public AuxiliarylineControls(Context context, AttributeSet attrs) {
        super(context, attrs);
		mContext = context;
        setMeasureAllChildren(true);
    }

    public AuxiliarylineControls(Context context) {
        super(context);
		mContext = context;
        setMeasureAllChildren(true);
    }
    

    @Override
    public void onFinishInflate() {
	super.onFinishInflate();
    }
    
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
	super.onLayout(changed, l, t, r, b);	
    }

}

