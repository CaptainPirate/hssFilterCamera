/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package googlog.com.hsscamera.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import googlog.com.hsscamera.R;
import googlog.com.hsscamerafilterlibrary.helper.LayoutChangeHelper;
import googlog.com.hsscamerafilterlibrary.helper.LayoutChangeNotifier;
import googlog.com.hsscamerafilterlibrary.helper.ApiHelper;

/**
 * A layout which handles the preview aspect ratio.
 */
public class PreviewFrameLayout extends RelativeLayout implements LayoutChangeNotifier {

    private static final String TAG = "PreviewFrameLayout";

    /** A callback to be invoked when the preview frame's size changes. */
    public interface OnSizeChangedListener {
        public void onSizeChanged(int width, int height);
    }

    private double mAspectRatio;
    private View mBorder;
    private OnSizeChangedListener mListener;
    private LayoutChangeHelper mLayoutChangeHelper;

    public PreviewFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAspectRatio(4.0 / 3.0);
        mLayoutChangeHelper = new LayoutChangeHelper(this);
    }

    @Override
    protected void onFinishInflate() {
       // mBorder = findViewById(R.id.preview_border);
        if (ApiHelper.HAS_FACE_DETECTION) {
            //ViewStub faceViewStub = (ViewStub) findViewById(R.id.face_view_stub);
            /* preview_frame_video.xml does not have face view stub, so we need to
             * check that.
             */
//            if (faceViewStub != null) {
//                faceViewStub.inflate();
//            }
        }
    }

    public void setAspectRatio(double ratio) {
        Log.d(TAG, "setAspectRatio(" + ratio + ") mAspectRatio=" + mAspectRatio + ", " + this);
        if (ratio <= 0.0) throw new IllegalArgumentException();

        if (mAspectRatio != ratio) {
            mAspectRatio = ratio;
            requestLayout();
        }
    }

    public void showBorder(boolean enabled) {
     //   mBorder.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

//    public void fadeOutBorder() {
//        Util.fadeOut(mBorder);
//    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int previewWidth = MeasureSpec.getSize(widthSpec);
        int previewHeight = MeasureSpec.getSize(heightSpec);

        if (!ApiHelper.HAS_SURFACE_TEXTURE) {
            // Get the padding of the border background.
            int hPadding = getPaddingLeft() + getPaddingRight();
            int vPadding = getPaddingTop() + getPaddingBottom();

            // Resize the preview frame with correct aspect ratio.
            previewWidth -= hPadding;
            previewHeight -= vPadding;

            boolean widthLonger = previewWidth > previewHeight;
            int longSide = (widthLonger ? previewWidth : previewHeight);
            int shortSide = (widthLonger ? previewHeight : previewWidth);
            if (longSide > shortSide * mAspectRatio) {
                longSide = (int) ((double) shortSide * mAspectRatio);
            } else {
                shortSide = (int) ((double) longSide / mAspectRatio);
            }
            if (widthLonger) {
                previewWidth = longSide;
                previewHeight = shortSide;
            } else {
                previewWidth = shortSide;
                previewHeight = longSide;
            }

            // Add the padding of the border.
            previewWidth += hPadding;
            previewHeight += vPadding;
        }

        // Ask children to follow the new preview dimension.
        super.onMeasure(MeasureSpec.makeMeasureSpec(previewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(previewHeight, MeasureSpec.EXACTLY));
        Log.d(TAG, "onMeasure() width = " + previewWidth + " height = " + previewHeight + ", "
                + this);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG,"w= "+w+"h= "+h+"oldw= "+oldw+"oldh= "+oldh);
        if (mListener != null) mListener.onSizeChanged(w, h);
    }

    @Override
    public void setOnLayoutChangeListener(
            LayoutChangeNotifier.Listener listener) {
        Log.d(TAG,"setOnLayoutChangeListener");
        mLayoutChangeHelper.setOnLayoutChangeListener(listener);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG,"onLayout");
        mLayoutChangeHelper.onLayout(changed, l, t, r, b);
    }
}
