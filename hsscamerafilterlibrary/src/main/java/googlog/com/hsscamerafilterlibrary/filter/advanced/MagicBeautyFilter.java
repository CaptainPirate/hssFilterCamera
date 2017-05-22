package googlog.com.hsscamerafilterlibrary.filter.advanced;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import googlog.com.hsscamerafilterlibrary.R;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageFilter;
import googlog.com.hsscamerafilterlibrary.utils.MagicParams;
import googlog.com.hsscamerafilterlibrary.utils.OpenGlUtils;

import java.nio.FloatBuffer;

/**
 * Created by Administrator on 2016/5/22.
 */
public class MagicBeautyFilter extends GPUImageFilter{
    private int mSingleStepOffsetLocation;
    private int mParamsLocation;

    public MagicBeautyFilter(){
        super(NO_FILTER_VERTEX_SHADER ,
                OpenGlUtils.readShaderFromRawResource(R.raw.beauty));
    }

    protected void onInit() {
        super.onInit();
        mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
        mParamsLocation = GLES20.glGetUniformLocation(getProgram(), "params");
        setBeautyLevel(MagicParams.beautyLevel);
    }

    private void setTexelSize(final float w, final float h) {
        setFloatVec2(mSingleStepOffsetLocation, new float[] {2.0f / w, 2.0f / h});
    }

    @Override
    public void onInputSizeChanged(final int width, final int height) {
        super.onInputSizeChanged(width, height);
        setTexelSize(width, height);
    }

    public void setBeautyLevel(int level){
        switch (level) {
            /*case 1:
                setFloat(mParamsLocation, 1.0f);
                break;
            case 2:
                setFloat(mParamsLocation, 0.8f);
                break;
            case 3:
                setFloat(mParamsLocation,0.6f);
                break;
            case 4:
                setFloat(mParamsLocation, 0.4f);
                break;
            case 5:
                setFloat(mParamsLocation,0.33f);
                break;*/
            case 1:
		setFloatVec4(mParamsLocation, new float[] {1.0f, 1.0f, 0.15f, 0.15f});
		break;
	    case 2:
		setFloatVec4(mParamsLocation, new float[] {0.8f, 0.9f, 0.2f, 0.2f});
		break;
	    case 3:
		setFloatVec4(mParamsLocation, new float[] {0.6f, 0.8f, 0.25f, 0.25f});
		break;
	    case 4:
		setFloatVec4(mParamsLocation, new float[] {0.4f, 0.7f, 0.38f, 0.3f});
	        break;
	    case 5:
		setFloatVec4(mParamsLocation, new float[] {0.33f, 0.63f, 0.4f, 0.35f});
		break;
            default:
                break;
        }
    }

    public void onBeautyLevelChanged(){
        setBeautyLevel(MagicParams.beautyLevel);
    }
}
