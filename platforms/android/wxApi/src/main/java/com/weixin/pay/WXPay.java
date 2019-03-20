package com.weixin.pay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WXPay {
    IWXAPI iwxapi;
    public WXPay(Context context,String appId, boolean checkSignature){
       iwxapi= WXAPIFactory.createWXAPI(context,appId,checkSignature);
       iwxapi.registerApp(appId);
    }
    public void pay(final JSONObject payJson){
        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId ="";
                try {
                    request.partnerId =payJson.getString("partnerId");
                    request.prepayId =payJson.getString("prepayId") ;
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = payJson.getString("nonceStr");
                    request.timeStamp = payJson.getString("timeStamp");
                    request.sign = payJson.getString("sign");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };

    }

}
