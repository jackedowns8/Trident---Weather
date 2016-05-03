#import "AdBuddizBridge.h"
#import "ContextAwareAdBuddizDelegate.h"
#import "ContextAwareAdBuddizRewardedVideoDelegate.h"

#import <AdBuddiz/AdBuddiz.h>

@implementation AdBuddizBridge

- (void)success:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [pluginResult setKeepCallbackAsBool: TRUE];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];    
}

- (void)error:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [pluginResult setKeepCallbackAsBool: TRUE];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];    
}

- (void)storeCallbackContext:(CDVInvokedUrlCommand*)command 
{
    ContextAwareAdBuddizDelegate* delegate = [[ContextAwareAdBuddizDelegate alloc] initWithPlugin:self command:(CDVInvokedUrlCommand*)command];
    [AdBuddiz setDelegate:delegate];

    ContextAwareAdBuddizRewardedVideoDelegate* rewardedVideoDelegate = [[ContextAwareAdBuddizRewardedVideoDelegate alloc] initWithPlugin:self command:(CDVInvokedUrlCommand*)command];
    [AdBuddiz.RewardedVideo setDelegate:rewardedVideoDelegate];
}

- (void)interstitial_setLogLevel:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    NSString* logLevel = [command.arguments objectAtIndex:0];
    if([logLevel isEqualToString:@"Info"]) {
        [AdBuddiz setLogLevel:ABLogLevelInfo];
    } else if([logLevel isEqualToString:@"Error"]) {
        [AdBuddiz setLogLevel:ABLogLevelError];
    } else if([logLevel isEqualToString:@"Silent"]) {
        [AdBuddiz setLogLevel:ABLogLevelSilent];
    }
    [self success:command];
}

- (void)interstitial_setPublisherKey:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    [AdBuddiz setPublisherKey:[command.arguments objectAtIndex:0]];
    [self success:command];
}

- (void)interstitial_setTestModeActive:(CDVInvokedUrlCommand*)command;
{
    [self storeCallbackContext: command];

    [AdBuddiz setTestModeActive];
    [self success:command];
}

- (void)interstitial_cacheAds:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    [AdBuddiz cacheAds];
    [self success:command];   
}

- (void)interstitial_isReadyToShowAd:(CDVInvokedUrlCommand*)command {
    [self storeCallbackContext: command];

    BOOL bIsReadyToShowAd = NO;
    if([command.arguments count] > 0 && [[command.arguments objectAtIndex:0] isKindOfClass:[NSString class]]) {
        bIsReadyToShowAd = [AdBuddiz isReadyToShowAd:[command.arguments objectAtIndex:0]];
    } else {
        bIsReadyToShowAd = [AdBuddiz isReadyToShowAd];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:(bIsReadyToShowAd?@"true":@"false")];
    [pluginResult setKeepCallbackAsBool: TRUE];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    [self success:command];
}

- (void)interstitial_showAd:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    if([command.arguments count] > 0 && [[command.arguments objectAtIndex:0] isKindOfClass:[NSString class]]) {
        [AdBuddiz showAd:[command.arguments objectAtIndex:0]];
    } else {
        [AdBuddiz showAd];
    }
    [self success:command];
}

- (void)rewardedvideo_fetch:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    [AdBuddiz.RewardedVideo fetch];
    [self success:command];  
}

- (void)rewardedvideo_show:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    [AdBuddiz.RewardedVideo show];
    [self success:command];  
}

- (void)rewardedvideo_isReadyToShow:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    BOOL bIsReadyToShow = [AdBuddiz.RewardedVideo isReadyToShow];

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:(bIsReadyToShow?@"true":@"false")];
    [pluginResult setKeepCallbackAsBool: TRUE];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    [self success:command];
}

- (void)rewardedvideo_setUserId:(CDVInvokedUrlCommand*)command
{
    [self storeCallbackContext: command];

    if([command.arguments count] > 0 && [[command.arguments objectAtIndex:0] isKindOfClass:[NSString class]]) {
        [AdBuddiz.RewardedVideo setUserId:[command.arguments objectAtIndex:0]];
        [self success:command];
    } else {
        [self error:command];
    }
}

- (void)logNative:(CDVInvokedUrlCommand*)command
{
	if([command.arguments count] > 0 && [[command.arguments objectAtIndex:0] isKindOfClass:[NSString class]]) {
		NSLog([command.arguments objectAtIndex:0]);
	}
	[self storeCallbackContext: command];
    [self success:command];
}

@end