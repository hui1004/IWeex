package com.weex.app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.weex.common.WXErrorCode;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.WXExceptionUtils;
import com.weex.app.hotreload.HotReloadManager;
import com.weex.app.util.AppConfig;
import com.weex.app.util.Constants;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;
import com.weex.app.util.UrlParse;

import org.json.JSONException;
import org.json.JSONObject;


public class WXPageActivity extends AbsWeexActivity implements
    WXSDKInstance.NestedInstanceInterceptor {

  private static final String TAG = "WXPageActivity";
  private ProgressBar mProgressBar;
  private TextView mTipView;
  private boolean mFromSplash = false;
  private HotReloadManager mHotReloadManager;

  @Override
  public void onCreateNestInstance(WXSDKInstance instance, NestedContainer container) {
    Log.d(TAG, "Nested Instance created.");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wxpage);
    mContainer = (ViewGroup) findViewById(R.id.container);
    mProgressBar = (ProgressBar) findViewById(R.id.progress);
    mTipView = (TextView) findViewById(R.id.index_tip);
    mTipView.setBackgroundColor(Color.parseColor("#FF6600"));
    mTipView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mTipView.setVisibility(View.GONE);
        }
    });

    Intent intent = getIntent();
    Uri uri = intent.getData();
    String from = intent.getStringExtra("from");
    mFromSplash = "splash".equals(from);
    /*是否是启动页过来的，决定要赋值的uri*/
    if(mFromSplash){
        mUri = Uri.parse(AppConfig.getLaunchUrl());
    }else{
        mUri = uri;
    }

    if (!WXSoInstallMgrSdk.isCPUSupport()) {
      mProgressBar.setVisibility(View.INVISIBLE);
      mTipView.setText(R.string.cpu_not_support_tip);
      return;
    }
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    String url;
    if(AppConfig.isDebug()){
      /*调试模式*/
      mHotReloadManager = new HotReloadManager(AppConfig.getDebugId(), new HotReloadManager.ActionListener() {
        @Override
        public void reload() {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(WXPageActivity.this, "Hot reload", Toast.LENGTH_SHORT).show();
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
    url =mUri.toString();
    loadUrl(url);
  }

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
      mTipView.setText("render error:" + errCode+msg);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(mFromSplash ? R.menu.main_scan : R.menu.main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  /*这里的操作要改造成悬浮调试工具*/
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:
        createWeexInstance();
        renderPage();
        break;
      case R.id.action_scan:
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
      case android.R.id.home:
        finish();
      default:
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
      if (result.getContents() == null) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
      } else {

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
}
