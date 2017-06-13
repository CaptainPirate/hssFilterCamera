
package googlog.com.hsscamera.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import googlog.com.hsscamera.R;

public abstract class CameraControls extends RotatableLayout {

    public CameraControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMeasureAllChildren(true);
    }

    public CameraControls(Context context) {
        super(context);

        setMeasureAllChildren(true);
    }
    
    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
    }
    
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int orientation = getResources().getConfiguration().orientation;
        int size = getResources().getDimensionPixelSize(R.dimen.camera_controls_size);
        int rotation = getUnifiedRotation();
        adjustBackground();
        // As l,t,r,b are positions relative to parents, we need to convert them
        // to child's coordinates
        r = r - l;
        b = b - t;
        l = 0;
        t = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            v.layout(l, t, r, b);
        }
        Rect shutter = new Rect();
        Rect modeSelecter = new Rect();
        if (size > 0) {
            // restrict controls to size
            switch (rotation) {
            case 0:
            case 180:
                l = (l + r - size) / 2;
                r = l + size;
                break;
            case 90:
            case 270:
                t = (t + b - size) / 2;
                b = t + size;
                break;
            }
        }
    }

    public void center(View v, int l, int t, int r, int b, int orientation, int rotation, Rect result) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        int tw = lp.leftMargin + v.getMeasuredWidth() + lp.rightMargin;
        int th = lp.topMargin + v.getMeasuredHeight() + lp.bottomMargin;
        switch (rotation) {
        case 0:
            // phone portrait; controls bottom
            result.left = (r + l) / 2 - tw / 2 + lp.leftMargin;
            result.right = (r + l) / 2 + tw / 2 - lp.rightMargin;
            result.bottom = b - lp.bottomMargin;
            result.top = b - th + lp.topMargin;
            break;
        case 90:
            // phone landscape: controls right
            result.right = r - lp.rightMargin;
            result.left = r - tw + lp.leftMargin;
            result.top = (b + t) / 2 - th / 2 + lp.topMargin;
            result.bottom = (b + t) / 2 + th / 2 - lp.bottomMargin;
            break;
        case 180:
            // phone upside down: controls top
            result.left = (r + l) / 2 - tw / 2 + lp.leftMargin;
            result.right = (r + l) / 2 + tw / 2 - lp.rightMargin;
            result.top = t + lp.topMargin;
            result.bottom = t + th - lp.bottomMargin;
            break;
        case 270:
            // reverse landscape: controls lefttopControlsViewNums
            result.left = l + lp.leftMargin;
            result.right = l + tw - lp.rightMargin;
            result.top = (b + t) / 2 - th / 2 + lp.topMargin;
            result.bottom = (b + t) / 2 + th / 2 - lp.bottomMargin;
            break;
        }
        v.layout(result.left, result.top, result.right, result.bottom);
    }

    public void toLeft(View v, Rect other, int rotation) {
        int tw = v.getMeasuredWidth() ;
        int th = v.getMeasuredHeight() ;
        int cx = (other.left - tw) / 3;
        int cy = (other.top + other.bottom) / 2;
        int l = 0, r = 0, t = 0, b = 0;

        // portrait, to left of anchor at bottom
        l = cx;
        r = cx + tw;
        
        t = cy - th / 2;
        b = cy + th / 2;

        v.layout(l, t, r, b);
    }

    public void toRight(View v, Rect other, int rotation, int right) {
        int tw = v.getMeasuredWidth();
        int th = v.getMeasuredHeight();
        int cx = (right - other.right - tw) / 3;
        int cy = (other.top + other.bottom) / 2;
        int l = 0, r = 0, t = 0, b = 0;

        l = other.right + cx * 2;
        r = right - cx;
        t = cy - th / 2;
        b = cy + th / 2;

        v.layout(l, t, r, b);
    }
    
    public void centerInui(View v , int l, int t, int r, int b){
        int instanceHeight = (b -t - v.getMeasuredHeight()) / 3;
        l +=  v.getMeasuredWidth() / 7;
        r -=  v.getMeasuredWidth() / 7;
        t += instanceHeight;
        b = t + v.getMeasuredHeight();
        v.layout(l, t, r, b);
    }
    
    public void centerTop(View v, int l, int t, int r, int b, int orientation, int rotation, Rect result) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        int tw = lp.leftMargin + v.getMeasuredWidth() + lp.rightMargin;
        int th = lp.topMargin + v.getMeasuredHeight() + lp.bottomMargin;

        switch (rotation) {
            case 0:
                // phone portrait; controls top
                result.left = (r + l) / 2 - tw / 2 + lp.leftMargin;
                result.right = (r + l) / 2 + tw / 2 - lp.rightMargin;
                result.bottom = t + th;
                result.top = t + lp.topMargin;
                break;
                
            case 90:
                // phone landscape: controls left
                result.right = l + tw;
                result.left = l + lp.leftMargin;
                result.top = (b + t) / 2 - th / 2 + lp.topMargin;
                result.bottom = (b + t) / 2 + th / 2 - lp.bottomMargin;
                break;
                
            case 180:
                // phone upside down: controls bottom
                result.left = (r + l) / 2 - tw / 2 + lp.leftMargin;
                result.right = (r + l) / 2 + tw / 2 - lp.rightMargin;
                result.top = b - th;
                result.bottom = b - lp.bottomMargin;
                break;
                
            case 270:
                // reverse landscape: controls right
                result.left = r - tw;
                result.right = r - lp.rightMargin;
                result.top = (b + t) / 2 - th / 2 + lp.topMargin;
                result.bottom = (b + t) / 2 + th / 2 - lp.bottomMargin;
                break;
                    
            default:
                break;
        }
        v.layout(result.left, result.top, result.right, result.bottom);
    }
    
    /**
     * Left of other
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     * @param other
     * @param rotation
     */
    public void leftLevel(View v, int l, int t, int r, int b, Rect other, int rotation) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        int tw = lp.leftMargin + v.getMeasuredWidth() + lp.rightMargin;
        int th = lp.topMargin + v.getMeasuredHeight() + lp.bottomMargin;
        int cx = (other.left + other.right) / 2;
        int cy = (other.top + other.bottom) / 2;
        switch (rotation) {
            case 0:
                // leftMargin
                l = lp.leftMargin;
                r = tw - lp.rightMargin;
                t = cy - th / 2 + lp.topMargin;
                b = cy + th / 2 - lp.bottomMargin;
                break;
                
            case 90:
                // bottomMargin
                l = cx - tw / 2 + lp.leftMargin;
                r = cx + tw / 2 - lp.rightMargin;
                t = b - th + lp.topMargin;
                b = b - lp.bottomMargin;
                break;
                
            case 180:
                // rightMargin
                l = r - tw + lp.leftMargin;
                r = r - lp.rightMargin;
                t = cy - th / 2 + lp.topMargin;
                b = cy + th / 2 - lp.bottomMargin;
                break;
                
            case 270:
                // topMargin
                l = cx - tw / 2 + lp.leftMargin;
                r = cx + tw / 2 - lp.rightMargin;
                t = lp.topMargin;
                b = th - lp.bottomMargin;
                break;
                        
            default:
                break;
        }
        v.layout(l, t, r, b);
    }
    
    /**
     * Right of other
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     * @param other
     * @param rotation
     */
    public void rightLevel(View v, int l, int t, int r, int b, Rect other, int rotation) {
        LayoutParams lp = (LayoutParams) v.getLayoutParams();
        int tw = lp.leftMargin + v.getMeasuredWidth() + lp.rightMargin;
        int th = lp.topMargin + v.getMeasuredHeight() + lp.bottomMargin;
        int cx = (other.left + other.right) / 2;
        int cy = (other.top + other.bottom) / 2;
        switch (rotation) {
            case 0:
                // rightMargin
                l = r - tw + lp.leftMargin;
                r = r - lp.rightMargin;
                t = cy - th / 2 + lp.topMargin;
                b = cy + th / 2 - lp.bottomMargin;
                break;
                
            case 90:
                // topMargin
                l = cx - tw / 2 + lp.leftMargin;
                r = cx + tw / 2 - lp.rightMargin;
                t = lp.topMargin;
                b = th - lp.bottomMargin;
                break;
                
            case 180:
                // leftMargin
                l = lp.leftMargin;
                r = tw - lp.rightMargin;
                t = cy - th / 2 + lp.topMargin;
                b = cy + th / 2 - lp.bottomMargin;
                break;
                
            case 270:
                // bottomMargin
                l = cx - tw / 2 + lp.leftMargin;
                r = cx + tw / 2 - lp.rightMargin;
                t = b - th + lp.topMargin;
                b = b - lp.bottomMargin;
                break;
                
            default:
                break;
        }
        v.layout(l, t, r, b);
    }
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
    public void adjustBackground() {
		/*
        int rotation = getUnifiedRotation();
        // remove current drawable and reset rotation
        mBackgroundView.setBackgroundDrawable(null);
        mBackgroundView.setRotationX(0);
        mBackgroundView.setRotationY(0);
        // if the switcher background is top aligned we need to flip the background
        // drawable vertically; if left aligned, flip horizontally
        switch (rotation) {
            case 180:
                mBackgroundView.setRotationX(180);
                break;
            case 270:
                mBackgroundView.setRotationY(180);
                break;
            default:
                break;
        }
       // mBackgroundView.setBackgroundResource(R.drawable.switcher_bg);
		*/
    }

}
// @} add by wmt 
