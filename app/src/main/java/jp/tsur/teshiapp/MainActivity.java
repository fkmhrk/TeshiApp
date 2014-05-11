package jp.tsur.teshiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.tsur.teshiapp.app.MainApp;
import jp.tsur.teshiapp.app.impl.MainAppImpl;
import jp.tsur.teshiapp.utils.Utils;


public class MainActivity extends Activity {

    private static final int REQUEST_GALLERY = 1;

    private MainApp mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mApp = new MainAppImpl();
        ButterKnife.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_GALLERY:
                // URI を File に変換してアップロード
                File file = Utils.getFileByUri(this, data.getData());
                if (file == null) {
                    Utils.showToast(this, getString(R.string.toast_failed));
                    return;
                }
                mApp.upload(file, getString(R.string.url_upload), new MainApp.UploadListener() {
                    @Override
                    public void onSuccess(String body) {
                        Utils.copyToClipboard(MainActivity.this, body);
                        Utils.showToast(MainActivity.this, getString(R.string.toast_success_copy_to_clipboard));
                    }

                    @Override
                    public void onError(Exception e) {
                        Utils.showToast(MainActivity.this, getString(R.string.toast_failed));
                    }
                });
                break;
        }
    }

    @OnClick(R.id.upload_image_button)
    void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @OnClick(R.id.matsuya_button)
    void onClickMatsuya() {
        Intent intent = new Intent(this, MatsuyaActivity.class);
        startActivity(intent);
    }
}
