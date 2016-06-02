var app = {
    initialize: function() {
        this.bindEvents();
    },

    bindEvents: function() {
		console.log("bindEvents");
        var self = this;
        document.addEventListener('deviceready', this.onDeviceReady, false);

        //OPTIONAL, to get more info about the SDK behavior
        document.addEventListener('AB-didCacheAd', function() { console.log("didCacheAd"); }, false);
        document.addEventListener('AB-didShowAd', function() { console.log("didShowAd"); }, false);
        document.addEventListener('AB-didFailToShowAd', function(e) { console.log(e.detail); }, false); //e.detail contains the error code
        document.addEventListener('AB-didClick', function() { console.log("didClick"); }, false);
        document.addEventListener('AB-didHideAd', function() { console.log("didHideAd"); }, false);

        // FOR REWARDED VIDEO ONLY
        // You must at least register an event listener on the AB-RewardedVideo-didComplete event to be notified when a user has watched a video.
        document.addEventListener('AB-RewardedVideo-didComplete', function() { self.giveRewardToUser(); }, false);
        
        // OPTIONAL, other events to get more info about the SDK behavior
        document.addEventListener('AB-RewardedVideo-didFetch', function() { console.log("didFetch"); }, false);
        document.addEventListener('AB-RewardedVideo-didFail', function(e) { console.log("didFail: "+e.detail); }, false); //e.detail contains the error code
        document.addEventListener('AB-RewardedVideo-didNotComplete', function() { console.log("didNotComplete"); }, false);
    },

    onDeviceReady: function() {
		console.log("deviceReady");
        AdBuddiz.setLogLevel(AdBuddiz.LogLevel.Info);                   // log level
        AdBuddiz.setTestModeActive();                                   // to delete before submitting to store
        AdBuddiz.setAndroidPublisherKey("TEST_PUBLISHER_KEY_ANDROID");  // replace with your Android app publisher key
        AdBuddiz.setIOSPublisherKey("TEST_PUBLISHER_KEY_IOS");          // replace with your iOS app publisher key
        
        AdBuddiz.cacheAds();                                           // start caching ads
    },
    
    giveRewardToUser: function() {
    	// Give the reward to the user. As example, we only display a dialog.
    	alert("Reward received!");
    }
};