package com.finddreams.languagelib;

/**
 * Created by lx on 2017/5/2.
 */

public class OnChangeLanguageEvent {
    public int languageType;
    public int refreshType;

    public OnChangeLanguageEvent(int languageType, int refreshType) {
        this.languageType = languageType;
        this.refreshType = refreshType;
    }
}
