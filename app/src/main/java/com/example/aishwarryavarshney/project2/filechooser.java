package com.example.aishwarryavarshney.project2;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.ImageFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class filechooser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;
    private Button button_camera;
    private Button button_camera2;
    private ValueCallback<Uri> mUploadMessage;
    private String filePath;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    // private final static int FILECHOOSER_RESULTCODE = 2888;
    Integer REQUEST_CAMERA = 1;
    private Uri mCapturedImageURI = null;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filechooser);
        webView = (WebView) findViewById(R.id.webView);
        //  button_camera = (Button) findViewById(R.id.button_camera);
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        webView.setWebViewClient(new MyBrowser());
        String qu = "?uid=" + abc.getId() + "&st=" + upload.getState() + "&ct=" + upload.getCity() + "&lol=" + upload.getLocality() + "&lt=" + upload.getLati() + "&lo=" + upload.getLongi();
        webView.loadUrl("https://ruralroadinfo.000webhostapp.com/imgupload.php" + qu);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);
        webView.setWebChromeClient(new WebChromeClient() {
            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                try{
                    if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;
                    }
                    //Toast.makeText(getApplicationContext(),filePathCallback.toString(),Toast.LENGTH_SHORT).show();
                    uploadMessage = filePathCallback;
                    if (filePathCallback!=null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP ) {
                            File imageStorageDir = new File(
                                    Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES)
                                    , "AndroidExampleFolder");

                            if (!imageStorageDir.exists()) {
                                // Create AndroidExampleFolder at sdcard
                                imageStorageDir.mkdirs();
                            }
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                                    Locale.getDefault()).format(new Date());
                            // Create camera captured image file path and name
                            File file = new File(
                                    imageStorageDir + File.separator
                                            + "IMG_" + timeStamp + ".jpg");
                            mCapturedImageURI = Uri.fromFile(file);
                            // Camera capture image intent
                            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                            i.addCategory(Intent.CATEGORY_OPENABLE);
                            i.setType("image/*");
                            // Create file chooser intent
                            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                            // Set camera intent to file chooser
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});
                            // On select image call onActivityResult method of activity
                                startActivityForResult(chooserIntent, REQUEST_SELECT_FILE);
                        }
                }
                catch (ActivityNotFoundException e) {
                    uploadMessage = null;
                    Toast.makeText(filechooser.this.getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

        });
    }

    @Override
    public void onRefresh() {
        webView.reload();
        refreshLayout.setRefreshing(false);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadData("sorry", "text/html", "utf-8");
            super.onReceivedError(view, request, error);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               //Toast.makeText(getApplicationContext(),String.valueOf(resultCode),Toast.LENGTH_SHORT).show();
                if (requestCode == REQUEST_SELECT_FILE ) {
                    if (resultCode == 0) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;

                    } else {
                        Uri[] results = null;
                        Uri result = intent == null ? mCapturedImageURI : intent.getData();
                        if (intent != null) {
                            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                        } else {
                            results = new Uri[]{result};
                            uploadMessage.onReceiveValue(results);
                        }
                        //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
                        uploadMessage = null;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}