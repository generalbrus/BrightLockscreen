/*
 *  BrightLockscreen Xposed Framework Module
 *  by Generalbrus@XDA
 *
 *  Copyright (C) 2015  generalbrus@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *   See <http://www.gnu.org/licenses/> for a copy of the GNU General Public License.
 *
 */

package com.generalbrus.brightlockscreen;

import android.content.res.XModuleResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.*;

public class BrightLockscreen implements IXposedHookZygoteInit, IXposedHookLoadPackage, IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;
    private static XSharedPreferences prefs;
    private float overlayAlpha = 0;
    private float securityOverlayAlpha = 0;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        prefs = new XSharedPreferences(BrightLockscreen.class.getPackage().getName());
        MODULE_PATH = startupParam.modulePath;
        XposedBridge.log("BrightLockscreen [Initialized]");
    }

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        prefs.reload();

        //Lock Screen Overlay
        if(prefs.getBoolean("pref_set_opacity", true)) {
            int opacity = prefs.getInt("pref_set_opacity_value", 0);
            overlayAlpha = opacity / 100f; //Alpha value between 0 (invisible) and 1 (fully visible

            findAndHookMethod("com.android.systemui.statusbar.phone.ScrimController", lpparam.classLoader, "setScrimBehindColor", float.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    boolean mKeyguardShowing  = getBooleanField(param.thisObject,"mKeyguardShowing");
                    if(mKeyguardShowing)
                       param.args[0] = overlayAlpha;

                }
            });
        }

        // Security Screen overlay
        if(prefs.getBoolean("pref_set_security_opacity", false)) {
            int securityOpacity = prefs.getInt("pref_set_security_opacity_value", 0);
            securityOverlayAlpha = securityOpacity / 100f;

            findAndHookMethod("com.android.systemui.statusbar.phone.ScrimController", lpparam.classLoader, "setScrimInFrontColor", float.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    boolean mBouncerShowing  = getBooleanField(param.thisObject,"mBouncerShowing");
                    boolean mDarkenWhileDragging  = getBooleanField(param.thisObject,"mDarkenWhileDragging");
                    boolean mExpanding  = getBooleanField(param.thisObject,"mExpanding");

                    if(mBouncerShowing && !(mExpanding && mDarkenWhileDragging))
                        param.args[0] = securityOverlayAlpha;
                }

               /* @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    View mScrimInFront  = (View) XposedHelpers.getObjectField(param.thisObject, "mScrimInFront");
                    boolean mDozing  = getBooleanField(param.thisObject,"mDozing");

                    if (securityOverlayAlpha != 1f) {
                        mScrimInFront.setClickable(false);
                    } else {
                        // Eat touch events (unless dozing).
                        mScrimInFront.setClickable(!mDozing);
                    }
                }*/
            });


        }
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        prefs.reload();
        //Replace text colour
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.android.systemui", "color", "clock_white", prefs.getInt("pref_clock_date_colour", 0xffffffff));


    }

}
