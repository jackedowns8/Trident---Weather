#import <Cordova/CDV.h>

@interface AdBuddizBridge : CDVPlugin

- (void)interstitial_setLogLevel:(CDVInvokedUrlCommand*)command;

- (void)interstitial_setPublisherKey:(CDVInvokedUrlCommand*)command;

- (void)interstitial_setTestModeActive:(CDVInvokedUrlCommand*)command;

- (void)interstitial_cacheAds:(CDVInvokedUrlCommand*)command;

- (void)interstitial_isReadyToShowAd:(CDVInvokedUrlCommand*)command;

- (void)interstitial_showAd:(CDVInvokedUrlCommand*)command;

- (void)rewardedvideo_fetch:(CDVInvokedUrlCommand*)command;

- (void)rewardedvideo_show:(CDVInvokedUrlCommand*)command;

- (void)rewardedvideo_isReadyToShow:(CDVInvokedUrlCommand*)command;

- (void)rewardedvideo_setUserId:(CDVInvokedUrlCommand*)command;

- (void)logNative:(CDVInvokedUrlCommand*)command;

@end
