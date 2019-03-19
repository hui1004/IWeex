package com.weex.iweex.receiver;

import android.content.Context;

import com.weex.iweex.util.MIUIUtils;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 * */
public class MyJPushMessageReceiver extends JPushMessageReceiver {
    int badgeCount=0;
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        if (!MIUIUtils.isMIUI()) {
            badgeCount++;
//            Toast.makeText(MVApplication.getInstance(),""+badgeCount,Toast.LENGTH_LONG).show();
            ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+
        }
    }
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage){
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
    }
}
