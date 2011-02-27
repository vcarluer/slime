//
//  Slime_V0_1AppDelegate.h
//  Slime_V0.1
//
//  Created by antonio Munoz on 18/02/11.
//  Copyright none 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@class RootViewController;

@interface Slime_V0_1AppDelegate : NSObject <UIApplicationDelegate> {
	UIWindow			*window;
	RootViewController	*viewController;
	
}

@property (nonatomic, retain) UIWindow *window;

@end
