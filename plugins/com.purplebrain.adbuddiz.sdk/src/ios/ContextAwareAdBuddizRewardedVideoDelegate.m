#import "ContextAwareAdBuddizRewardedVideoDelegate.h"

#define REWARDED_VIDEO_EVENT_PREFIX @"AB-RewardedVideo-" 

@implementation ContextAwareAdBuddizRewardedVideoDelegate

- (void)didFetch
{
	[self dispatchJavascriptEvent:REWARDED_VIDEO_EVENT_PREFIX @"didFetch"];
}

- (void)didFail:(AdBuddizError) error
{
	[self dispatchJavascriptEvent:REWARDED_VIDEO_EVENT_PREFIX @"didFail" content:[AdBuddiz nameForError:error]];
}

- (void)didComplete
{
	[self dispatchJavascriptEvent:REWARDED_VIDEO_EVENT_PREFIX @"didComplete"];
}

- (void)didNotComplete
{
	[self dispatchJavascriptEvent:REWARDED_VIDEO_EVENT_PREFIX @"didNotComplete"];
}

@end