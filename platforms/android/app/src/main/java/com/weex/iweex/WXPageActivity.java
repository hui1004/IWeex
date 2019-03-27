package com.weex.iweex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.WXExceptionUtils;
import com.weex.iweex.hotreload.HotReloadManager;
import com.weex.iweex.util.AppConfig;
import com.weex.iweex.util.Constants;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;
import com.weex.iweex.util.UrlParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;


public class WXPageActivity extends AbsWeexActivity implements
    WXSDKInstance.NestedInstanceInterceptor,OnClickListener{

  private static final String TAG = "WXPageActivity";
  private ProgressBar mProgressBar;
  private TextView mTipView;
  private boolean mFromSplash = false;
  private HotReloadManager mHotReloadManager;
  private LinearLayout debug_tool;
  private ImageView scanner,refresh;
  private TextView title;
  private LinearLayout rightItem;
  private TextView rightItemText;
  private LinearLayout back;
  @Override
  public void onCreateNestInstance(WXSDKInstance instance, NestedContainer container) {
    Log.d(TAG, "Nested Instance created.");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wxpage);
//    setStatusBar();
    mContainer = (ViewGroup) findViewById(R.id.container);
    mProgressBar = (ProgressBar) findViewById(R.id.progress);
    mTipView = (TextView) findViewById(R.id.index_tip);
    scanner=(ImageView)findViewById(R.id.scanner);
    refresh=(ImageView)findViewById(R.id.refresh);
    debug_tool=findViewById(R.id.debug_tool);
    rightItem=findViewById(R.id.right_item);
    title=findViewById(R.id.title_text);
    rightItemText=findViewById(R.id.right_item_text);
    back=findViewById(R.id.back_item);

    mTipView.setBackgroundColor(Color.parseColor("#FF6600"));
    scanner.setOnClickListener(this);
    refresh.setOnClickListener(this);
    mTipView.setOnClickListener(this);
    back.setOnClickListener(this);
    rightItem.setOnClickListener(this);
    Intent intent = getIntent();
    String url= intent.getStringExtra(Constants.URL);
    String from = intent.getStringExtra("from");
    String title = intent.getStringExtra(Constants.TITLE);
    mFromSplash = "splash".equals(from);
    /*是否是启动页过来的，决定要赋值的uri*/
    if(mFromSplash){
        url =AppConfig.getLaunchUrl(this);
    }
    if (!WXSoInstallMgrSdk.isCPUSupport()) {
      mProgressBar.setVisibility(View.INVISIBLE);
      mTipView.setText(R.string.cpu_not_support_tip);
      return;
    }

    if(AppConfig.isDebug()){
        if(mFromSplash){
            /*显示扫码悬浮窗*/
           debug_tool.setVisibility(View.VISIBLE);
        }
      /*调试模式*/
      initHotReloadManager();
    }
    setTitle(title);
    loadUrl(url);
  }
    public void setTitle(String title){
         this.title.setText(title);
        //  toolbar.setNavigationIcon();
    }
    public TextView getToolbarTitle() {
        return title;
    }
    public TextView getToolbarRightItem() {
        return title;
    }
    public LinearLayout getToolbarLeftItem() {
        return back;
    }
    public RelativeLayout getToolbar() {
        return findViewById(R.id.toolbar);
    }
    private void initHotReloadManager(){
      /*调试模式*/
      mHotReloadManager = new HotReloadManager(AppConfig.getDebugId(), new HotReloadManager.ActionListener() {
          @Override
          public void reload() {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
//              Toast.makeText(WXPageActivity.this, "Hot reload", Toast.LENGTH_SHORT).show();
                      createWeexInstance();
                      renderPage();
                  }
              });
          }

          @Override
          public void render(final String bundleUrl) {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      Toast.makeText(WXPageActivity.this, "Render: " + bundleUrl, Toast.LENGTH_SHORT).show();
                      createWeexInstance();
                      loadUrl(bundleUrl);
                  }
              });
          }
      });
    }
  /*设置状态栏*/
 public void setStatusBar(){
     if(OSVersion()>=19)
     {
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
     }
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         getWindow().setStatusBarColor(Color.TRANSPARENT);
     }
 }
    /*设置状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    public void setStatusBarShow(){
        if(OSVersion()>=19)
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //设置状态栏颜色
            getWindow().setStatusBarColor(Color.parseColor("#3eb4ff"));
        }


    }
  private  int OSVersion() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }
  /*界面开始渲染之前显示加载中进度条*/
  protected void preRenderPage() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Intent intent = new Intent("requestPermission");
    intent.putExtra("REQUEST_PERMISSION_CODE", requestCode);
    intent.putExtra("permissions", permissions);
    intent.putExtra("grantResults", grantResults);
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }


  @Override
  public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    mProgressBar.setVisibility(View.GONE);
    mTipView.setVisibility(View.GONE);
  }

  @Override
  public void onException(WXSDKInstance instance, String errCode, String msg) {
    mProgressBar.setVisibility(View.GONE);
    mTipView.setVisibility(View.VISIBLE);
    if (TextUtils.equals(errCode, WXErrorCode.WX_DEGRAD_ERR_NETWORK_BUNDLE_DOWNLOAD_FAILED.getErrorCode())) {
      mTipView.setText(R.string.index_tip);
    } else {
      mTipView.setText("render error:" + errCode+msg+"\\/n"+"---------点击重试---------");
    }
  }
    @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

    if (result != null) {
      if (result.getContents() == null) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
      } else {
          String url=result.getContents();
          Uri uri=Uri.parse(url);
          if (uri.getQueryParameter(Constants.WEEX_TPL_KEY)!=null){
              createWeexInstance();
              mUrl=uri.getQueryParameter(Constants.WEEX_TPL_KEY);
          }else{
              mUrl=url;
          }
          Uri debugUri=Uri.parse(mUrl);
          String debugIp=debugUri.getScheme()+"://"+debugUri.getHost()+":"+(debugUri.getPort()+1);
          AppConfig.setDebugId(debugIp);
          mHotReloadManager.destroy();
          initHotReloadManager();
          AppConfig.setDebugUrl(this,mUrl);
          refreshPage();
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
  @Override
  public void onDestroy() {
    super.onDestroy();
    if (mHotReloadManager != null) {
      mHotReloadManager.destroy();
    }
  }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.index_tip:
              mTipView.setVisibility(View.GONE);
              createWeexInstance();
              renderPage();
              break;
          case R.id.scanner:
              IntentIntegrator integrator = new IntentIntegrator(this);
              integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
              integrator.setPrompt("Scan a barcode");
              //integrator.setCameraId(0);  // Use a specific camera of the device
              integrator.setBeepEnabled(true);
              integrator.setOrientationLocked(false);
              integrator.setBarcodeImageEnabled(true);
              integrator.setPrompt(getString(R.string.capture_qrcode_prompt));
              integrator.initiateScan();
              break;
          case R.id.refresh:
              createWeexInstance();
              mTipView.setVisibility(View.GONE);
              refreshPage();
              break;
          case R.id.back_item:
              destoryWeexInstance();
              finish();
              break;
          case R.id.right_item:

              break;
          default:
              break;
      }

    }
}
