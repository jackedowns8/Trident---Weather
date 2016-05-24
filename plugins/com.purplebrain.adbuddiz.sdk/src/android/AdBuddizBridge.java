package com.purplebrain.adbuddiz.sdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AdBuddizBridge extends CordovaPlugin {

    private final static boolean SHOULD_LOG = false;
    private static final String TAG = AdBuddizBridge.class.getSimpleName();

    private static final String LOG_NATIVE_ACTION = "logNative";
    
    private static final String INTERSTITIAL_ACTION_PREFIX = "interstitial_";
    private static final String SET_LOG_LEVEL_ACTION = INTERSTITIAL_ACTION_PREFIX+"setLogLevel";
    private static final String SET_PUBLISHER_KEY_ACTION =INTERSTITIAL_ACTION_PREFIX+ "setPublisherKey";
    private static final String SET_TEST_MODE_ACTIVE_ACTION = INTERSTITIAL_ACTION_PREFIX+"setTestModeActive";
    private static final String CACHE_ADS_ACTION = INTERSTITIAL_ACTION_PREFIX+"cacheAds";
    private static final String IS_READY_TO_SHOW_AD_ACTION = INTERSTITIAL_ACTION_PREFIX+"isReadyToShowAd";
    private static final String SHOW_AD_ACTION = INTERSTITIAL_ACTION_PREFIX+"showAd";
    
    private static final String REWARDED_VIDEO_ACTION_PREFIX = "rewardedvideo_";
    private static final String FETCH_ACTION = REWARDED_VIDEO_ACTION_PREFIX+"fetch";
    private static final String IS_READY_TO_SHOW_ACTION = REWARDED_VIDEO_ACTION_PREFIX+"isReadyToShow";
    private static final String SHOW_ACTION = REWARDED_VIDEO_ACTION_PREFIX+"show";
    private static final String SET_USER_ID_ACTION = REWARDED_VIDEO_ACTION_PREFIX+"setUserId";

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action == null) {
			return false;
		}
		
        boolean result = false;
        if (LOG_NATIVE_ACTION.equals(action)) {
        	Log.i("AdBuddiz", args.getString(0));
        } else if (action.startsWith(REWARDED_VIDEO_ACTION_PREFIX)) {
        	result = executeIncentivizedAction(action, args, callbackContext);
        } else if (action.startsWith(INTERSTITIAL_ACTION_PREFIX)){
        	result = executeStandardAction(action, args, callbackContext);
        }
        if (result) {
	        success(callbackContext);
	        storeCallbackContext(callbackContext);
        }
        return result;
    }
	
	private boolean executeStandardAction(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (SET_LOG_LEVEL_ACTION.equals(action)) {
            setLogLevel(args.getString(0));
        } else if (SET_PUBLISHER_KEY_ACTION.equals(action)) {
            AdBuddiz.setPublisherKey(args.getString(0));
        } else if(SET_TEST_MODE_ACTIVE_ACTION.equals(action)) {
            AdBuddiz.setTestModeActive();
        } else if(CACHE_ADS_ACTION.equals(action)) {
            AdBuddiz.cacheAds(this.cordova.getActivity());
        } else if(IS_READY_TO_SHOW_AD_ACTION.equals(action)) {
            isReadyToShowAd(args, callbackContext);
        } else if(SHOW_AD_ACTION.equals(action)) {
            showAd(args);
        } else {
            return false;
        }
		return true;
	}
	
	private boolean executeIncentivizedAction(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (FETCH_ACTION.equals(action)) {
			AdBuddiz.RewardedVideo.fetch(this.cordova.getActivity());
		} else if (IS_READY_TO_SHOW_ACTION.equals(action)) {
			isReadyToShowVideoAd(callbackContext);
		} else if (SHOW_ACTION.equals(action)) {
			AdBuddiz.RewardedVideo.show(this.cordova.getActivity());
		} else if (SET_USER_ID_ACTION.equals(action)) {
			AdBuddiz.RewardedVideo.setUserId(args.getString(0));
		} else {
			return false;
		}
		return true;
	}

    private void setLogLevel(String sLogLevel) {
        for(AdBuddizLogLevel logLevel : AdBuddizLogLevel.values()) {
            if(logLevel.name().equals(sLogLevel)) {
                AdBuddiz.setLogLevel(logLevel);
            }
        }
    }

    private void isReadyToShowAd(JSONArray args, CallbackContext callbackContext) throws JSONException {
        boolean isReadyToShowAd = false;
        if(args.length() > 0) {
            isReadyToShowAd = AdBuddiz.isReadyToShowAd(this.cordova.getActivity(), args.getString(0));
        } else {
            isReadyToShowAd = AdBuddiz.isReadyToShowAd(this.cordova.getActivity());
        }
        callIsReadyToShowCallback(isReadyToShowAd, callbackContext);
    }
    
    private void isReadyToShowVideoAd(CallbackContext callbackContext) throws JSONException {
    	boolean isReadyToShowVideoAd = AdBuddiz.RewardedVideo.isReadyToShow(this.cordova.getActivity());
    	callIsReadyToShowCallback(isReadyToShowVideoAd, callbackContext);
    }
    
    private void callIsReadyToShowCallback(boolean isReadyToShow, CallbackContext callbackContext) throws JSONException {
        PluginResult result = new PluginResult(PluginResult.Status.OK, Boolean.toString(isReadyToShow));
        result.setKeepCallback(true);
        callbackContext.sendPluginResult(result);
    }

    private void showAd(JSONArray args) throws JSONException {
        if(args.length() > 0) {
            AdBuddiz.showAd(this.cordova.getActivity(), args.getString(0));
        } else {
            AdBuddiz.showAd(this.cordova.getActivity());
        }
    }
    
    private void success(CallbackContext callbackContext) {
        PluginResult result = new PluginResult(PluginResult.Status.OK);
        result.setKeepCallback(true);
        callbackContext.sendPluginResult(result);
    }

    private void storeCallbackContext(CallbackContext callbackContext) {
    	ContextAwareAdBuddizDelegate delegate = new ContextAwareAdBuddizDelegate(callbackContext);
        AdBuddiz.setDelegate(delegate);
        AdBuddiz.RewardedVideo.setDelegate(delegate);
    }

    private static class ContextAwareAdBuddizDelegate
        implements AdBuddizDelegate, AdBuddizRewardedVideoDelegate {

        private static final String INTERSTITIAL_EVENT_PREFIX = "AB-";
        private static final String REWARDED_VIDEO_EVENT_PREFIX = "AB-RewardedVideo-";

        private CallbackContext callbackContext;

        private ContextAwareAdBuddizDelegate(CallbackContext callbackContext) {
            this.callbackContext = callbackContext;
        }

        private void dispatchJavascriptEvent(String eventName) {
            dispatchJavascriptEvent(eventName, null);
        }

        private void dispatchJavascriptEvent(String eventName, String content) {
            String eventToDispatch = eventName + ((content != null) ? (":" + content) : "");
            PluginResult result = new PluginResult(PluginResult.Status.OK, eventToDispatch);
            result.setKeepCallback(true);
            callbackContext.sendPluginResult(result);
        }

        @Override
        public void didCacheAd() {
            dispatchJavascriptEvent(INTERSTITIAL_EVENT_PREFIX + "didCacheAd");
        }

        @Override
        public void didShowAd() {
            dispatchJavascriptEvent(INTERSTITIAL_EVENT_PREFIX + "didShowAd");
        }

        @Override
        public void didFailToShowAd(AdBuddizError error) {
            dispatchJavascriptEvent(INTERSTITIAL_EVENT_PREFIX + "didFailToShowAd", error.name());
        }

        @Override
        public void didClick() {
            dispatchJavascriptEvent(INTERSTITIAL_EVENT_PREFIX + "didClick");
        }

        @Override
        public void didHideAd() {
            dispatchJavascriptEvent(INTERSTITIAL_EVENT_PREFIX + "didHideAd");
        }
        
        @Override
        public void didFetch() {
        	dispatchJavascriptEvent(REWARDED_VIDEO_EVENT_PREFIX + "didFetch");
        }
        
        @Override
        public void didFail(AdBuddizRewardedVideoError error) {
        	dispatchJavascriptEvent(REWARDED_VIDEO_EVENT_PREFIX + "didFail", error.name());
        }

        @Override
        public void didComplete() {
        	dispatchJavascriptEvent(REWARDED_VIDEO_EVENT_PREFIX + "didComplete");
        }
        
        @Override
        public void didNotComplete() {
        	dispatchJavascriptEvent(REWARDED_VIDEO_EVENT_PREFIX + "didNotComplete");
        }
    }

}