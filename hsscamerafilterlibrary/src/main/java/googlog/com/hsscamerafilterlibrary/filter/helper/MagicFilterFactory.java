package googlog.com.hsscamerafilterlibrary.filter.helper;

import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicAmaroFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicAntiqueFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicBlackCatFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicBrannanFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicBrooklynFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicCalmFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicCoolFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicCrayonFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicEarlyBirdFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicEmeraldFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicEvergreenFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicFairytaleFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicFreudFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicHealthyFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicHefeFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicHudsonFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicImageAdjustFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicInkwellFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicKevinFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicLatteFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicLomoFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicN1977Filter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicNashvilleFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicNostalgiaFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicPixarFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicRiseFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicRomanceFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSakuraFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSierraFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSketchFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSkinWhitenFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSunriseFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSunsetFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSutroFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicSweetsFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicTenderFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicToasterFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicValenciaFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicWaldenFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicWarmFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicWhiteCatFilter;
import googlog.com.hsscamerafilterlibrary.filter.advanced.MagicXproIIFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageBrightnessFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageContrastFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageExposureFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageHueFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageSaturationFilter;
import googlog.com.hsscamerafilterlibrary.filter.base.gpuimage.GPUImageSharpenFilter;

public class MagicFilterFactory{
	
	private static MagicFilterType filterType = MagicFilterType.NONE;
	
	public static GPUImageFilter initFilters(MagicFilterType type){
		filterType = type;
		switch (type) {
		case WHITECAT:
			return new MagicWhiteCatFilter();
		case BLACKCAT:
			return new MagicBlackCatFilter();
		case SKINWHITEN:
			return new MagicSkinWhitenFilter();
		case ROMANCE:
			return new MagicRomanceFilter();
		case SAKURA:
			return new MagicSakuraFilter();
		case AMARO:
			return new MagicAmaroFilter();
		case WALDEN:
			return new MagicWaldenFilter();
		case ANTIQUE:
			return new MagicAntiqueFilter();
		case CALM:
			return new MagicCalmFilter();
		case BRANNAN:
			return new MagicBrannanFilter();
		case BROOKLYN:
			return new MagicBrooklynFilter();
		case EARLYBIRD:
			return new MagicEarlyBirdFilter();
		case FREUD:
			return new MagicFreudFilter();
		case HEFE:
			return new MagicHefeFilter();
		case HUDSON:
			return new MagicHudsonFilter();
		case INKWELL:
			return new MagicInkwellFilter();
		case KEVIN:
			return new MagicKevinFilter();
		case LOMO:
			return new MagicLomoFilter();
		case N1977:
			return new MagicN1977Filter();
		case NASHVILLE:
			return new MagicNashvilleFilter();
		case PIXAR:
			return new MagicPixarFilter();
		case RISE:
			return new MagicRiseFilter();
		case SIERRA:
			return new MagicSierraFilter();
		case SUTRO:
			return new MagicSutroFilter();
		case TOASTER2:
			return new MagicToasterFilter();
		case VALENCIA:
			return new MagicValenciaFilter();
		case XPROII:
			return new MagicXproIIFilter();
		case EVERGREEN:
			return new MagicEvergreenFilter();
		case HEALTHY:
			return new MagicHealthyFilter();
		case COOL:
			return new MagicCoolFilter();
		case EMERALD:
			return new MagicEmeraldFilter();
		case LATTE:
			return new MagicLatteFilter();
		case WARM:
			return new MagicWarmFilter();
		case TENDER:
			return new MagicTenderFilter();
		case SWEETS:
			return new MagicSweetsFilter();
		case NOSTALGIA:
			return new MagicNostalgiaFilter();
		case FAIRYTALE:
			return new MagicFairytaleFilter();
		case SUNRISE:
			return new MagicSunriseFilter();
		case SUNSET:
			return new MagicSunsetFilter();
		case CRAYON:
			return new MagicCrayonFilter();
		case SKETCH:
			return new MagicSketchFilter();
		//image adjust
		case BRIGHTNESS:
			return new GPUImageBrightnessFilter();
		case CONTRAST:
			return new GPUImageContrastFilter();
		case EXPOSURE:
			return new GPUImageExposureFilter();
		case HUE:
			return new GPUImageHueFilter();
		case SATURATION:
			return new GPUImageSaturationFilter();
		case SHARPEN:
			return new GPUImageSharpenFilter();
		case IMAGE_ADJUST:
			return new MagicImageAdjustFilter();
		default:
			return null;
		}
	}
	
	public MagicFilterType getCurrentFilterType(){
		return filterType;
	}
}
