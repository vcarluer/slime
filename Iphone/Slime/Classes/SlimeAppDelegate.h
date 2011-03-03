//
//  SlimeAppDelegate.h
//  Slime
//
//  Created by antonio Munoz on 14/02/11.
//  Copyright none 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@class RootViewController;

@interface SlimeAppDelegate : NSObject <UIApplicationDelegate> {
	UIWindow			*window;
	RootViewController	*viewController;
}

@property (nonatomic, retain) UIWindow *window;

@end
