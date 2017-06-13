/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2014. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */
package googlog.com.hsscamera.view.edit.platform;

import java.io.FileDescriptor;

import android.media.CamcorderProfile;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import googlog.com.hsscamera.view.edit.guideline.AuxiliarylineManager;

public interface ICameraAppUi {

    public enum CommonUiType {
        SHUTTER,
        MODE_PICKER,
        THUMBNAIL,
        PICKER,
        INDICATOR,
        REMAINING,
        INFO,       //info manager
        REVIEW,     //review manager
        ROTATE_PROGRESS,
        ROTATE_DIALOG,
        ZOOM,
        SETTING,
        FACE_BEAUTY_ENTRY,
        BEAUTY,
		AUXILIARY_LINE,//GCH [auxiliary UI]
		DELAY_SHOT,//GCH self time
    }

    public enum SpecViewType {
        MODE_FACE_BEAUTY,
        MODE_PANORAMA,
        MODE_PIP,
        MODE_STEREO,
        MODE_SLOW_MOTION,
        ADDITION_CONTINUE_SHOT,
        ADDITION_EFFECT,
        ADDITION_OBJECT_TRACKING,
    }



    public ICameraView getCameraView(CommonUiType type);

    public ICameraView getCameraView(SpecViewType mode);


    // CommonUiType --> REVIEW
    /**
     * set the review's onclick Listener ,such as ok/cancel/retake/play
     */
    public void setReviewListener(OnClickListener retatekListener, OnClickListener playListener);

    public void showReview(String path, FileDescriptor fd);

    public void hideReview();

    public void setReviewCompensation(int compensation);

	public AuxiliarylineManager getAuxiliarylineManager();//GCH [auxiliary UI]

    public ViewGroup getBottomViewLayer();

    public ViewGroup getNormalViewLayer() ;


}
