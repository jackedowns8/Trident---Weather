#import "AbstractContextAwareAdBuddizDelegate.h"

@interface AbstractContextAwareAdBuddizDelegate ()

@property CDVPlugin* cdvPlugin;
@property NSString* callbackId;

@end

@implementation AbstractContextAwareAdBuddizDelegate

@synthesize cdvPlugin;
@synthesize callbackId;

- (id)initWithPlugin:(CDVPlugin*)plugin command:(CDVInvokedUrlCommand*)command
{
	cdvPlugin = plugin;
	callbackId = command.callbackId;
	return self;
}

- (void)dispatchJavascriptEvent:(NSString*)eventName
{
    [self dispatchJavascriptEvent:eventName content:nil];
}

- (void)dispatchJavascriptEvent:(NSString*)eventName content:(NSString*)content
{
    NSMutableString* eventToDispatch = [[NSMutableString alloc] init];
    [eventToDispatch setString: eventName];
    if(content != nil) {
		[eventToDispatch appendFormat:@":%@", content];
    }
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:eventToDispatch];
    [pluginResult setKeepCallbackAsBool: TRUE];
    [cdvPlugin.commandDelegate sendPluginResult:pluginResult callbackId:callbackId];
}

@end