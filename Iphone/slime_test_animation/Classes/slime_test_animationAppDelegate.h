//
//  slime_test_animationAppDelegate.h
//  slime_test_animation
//
//  Created by antonio Munoz on 18/02/11.
//  Copyright none 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@class RootViewController;

@interface slime_test_animationAppDelegate : NSObject <UIApplicationDelegate> {
	UIWindow			*window;
	RootViewController	*viewController;
}

@property (nonatomic, retain) UIWindow *window;

@end
