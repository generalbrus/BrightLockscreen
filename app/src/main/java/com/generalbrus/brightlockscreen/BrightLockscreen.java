/*
 * Brightockscreen Xposed Framework Module
 * By Generalbrys@XDA
 *
 * Removes dark overlay on Lollipop lock screen wallpaper.
 *
 * This code was not made public, you should not be reading this.
 */
package com.generalbrus.brightlockscreen;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.*;

public class BrightLockscreen implements IXposedHookLoadPackage {

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.phone.ScrimController", lpparam.classLoader, "setScrimBehindColor", float.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                boolean mKeyguardShowing  = getBooleanField(param.thisObject,"mKeyguardShowing");
                if(mKeyguardShowing)
                   param.args[0]=0; //scrim alpha value
            }
        });
    }

}
