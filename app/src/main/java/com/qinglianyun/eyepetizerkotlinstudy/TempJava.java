package com.qinglianyun.eyepetizerkotlinstudy;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by tang_xqing on 2019/12/3.
 */
public class TempJava {
    void gett(Context context){
        TextView view = new TextView(context);
        TempClassKt.isBlod(view);

        TempCallClass tempCallClass = new TempCallClass();
        tempCallClass.call(context);

    }
}