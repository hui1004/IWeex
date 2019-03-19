package com.weex.iweex.modle;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
/*界面间传参类*/
public  class  ParamsModule extends WXModule {
    private static JSONObject params=new JSONObject();
    public static void  putParam(JSONObject params){
        ParamsModule.params =params;
    }
    @JSMethod
    public void getParam(JSCallback callback){
         callback.invoke( ParamsModule.params);
    }
}
