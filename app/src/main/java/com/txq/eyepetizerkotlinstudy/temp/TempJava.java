package com.txq.eyepetizerkotlinstudy.temp;

import android.content.Context;
import android.widget.TextView;

import com.txq.base.utils.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tang_xqing on 2019/12/3.
 */
class TempJava {
    void gett(Context context) {
        TextView view = new TextView(context);
        TempClassKt.isBlod(view);

        TempCallClass tempCallClass = new TempCallClass();
        tempCallClass.call(context);

        LogUtils.Companion.d("", "");
        String s = new String();
        s.hashCode();

        // Kotlin中hashcode() 方法 与java中的一致。  或者说底层调用了Java代码？
        Float dd = 1.24343f;
        dd.hashCode();

        List<Number> oList = new ArrayList<>();
        List<? extends Integer> sList = new ArrayList<Integer>();
        oList.add(sList.get(0));
//        sList.add(oList.get(0));
        Object object = sList.get(0);
        Integer integer = sList.get(0);


        ArrayList<? extends TextView> eList = new ArrayList<>();
//        eList.add(new TextView(null));            // 为什么无法添加本类TextView
//        eList.add(new EditText(null));         // 为什么不能添加子类和父类，上面代码却可以呢？
//        eList.add(new MyEditText(null,null));   // 为什么不能添加子类和父类，上面代码却可以呢？

        ArrayList<? super MyEditText> nList = new ArrayList<>();
        nList.add(new MyEditText(null, null));

//        nList.add(new EditText(null));      // 泛型类型为基类限定符，为什么不可以添加父类呢
        // 因为List不是协变？
    }
}