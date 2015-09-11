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
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.*;

public class BrightLockscreen implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private static XSharedPreferences prefs;
    private float overlayAlpha = 0;
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        prefs = new XSharedPreferences(BrightLockscreen.class.getPackage().getName());
    }
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        if(prefs.getBoolean("pref_set_opacity", true)) {
            int opacity = prefs.getInt("pref_set_opacity_value", 0);
            overlayAlpha = opacity / 100f; //alpha between 0 (invisible) and 1 (fully visible)
            findAndHookMethod("com.android.systemui.statusbar.phone.ScrimController", lpparam.classLoader, "setScrimBehindColor", float.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    boolean mKeyguardShowing  = getBooleanField(param.thisObject,"mKeyguardShowing");
                    if(mKeyguardShowing)
                       param.args[0] = overlayAlpha; //scrim alpha value
                }
            });
        }
    }


}
