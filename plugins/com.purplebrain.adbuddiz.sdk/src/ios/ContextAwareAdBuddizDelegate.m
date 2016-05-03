#import "ContextAwareAdBuddizDelegate.h"

#define INTERSTITIAL_EVENT_PREFIX @"AB-" 

@implementation ContextAwareAdBuddizDelegate

- (void)didCacheAd
{
	[self dispatchJavascriptEvent:INTERSTITIAL_EVENT_PREFIX @"didCacheAd"];
}

- (void)didShowAd 
{
	[self dispatchJavascriptEvent:INTERSTITIAL_EVENT_PREFIX @"didShowAd"];
}

- (void)didFailToShowAd:(AdBuddizError) error
{
	[self dispatchJavascriptEvent:INTERSTITIAL_EVENT_PREFIX @"didFailToShowAd" content:[AdBuddiz nameForError:error]];
}

- (void)didClick
{
	[self dispatchJavascriptEvent:INTERSTITIAL_EVENT_PREFIX @"didClick"];
}

- (void)didHideAd
{
	[self dispatchJavascriptEvent:INTERSTITIAL_EVENT_PREFIX @"didHideAd"];
}

@end