#import <Cordova/CDV.h>
#import <AdBuddiz/AdBuddiz.h>

@interface AbstractContextAwareAdBuddizDelegate : NSObject

- (id)initWithPlugin:(CDVPlugin*)plugin command:(CDVInvokedUrlCommand*)command;

- (void)dispatchJavascriptEvent:(NSString*)eventName;
- (void)dispatchJavascriptEvent:(NSString*)eventName content:(NSString*)content;

@end