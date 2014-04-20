package jp.tsur.teshiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.Utils;


public class MainActivity extends Activity {

    private static final int REQUEST_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                new UploadTask().execute(file);
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

    private class UploadTask extends AsyncTask<File, Void, String> {
        @Override
        protected String doInBackground(File... files) {
            // ポスト先のURLを指定
            HttpPost httpPost = new HttpPost(getString(R.string.url_upload));
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            // ファイル名&パスを指定
            FileBody fileBody = new FileBody(files[0]);

            // KEYとファイルを指定
            multipartEntity.addPart("imagedata", fileBody);
            httpPost.setEntity(multipartEntity);

            // レスポンス取得
            HttpClient httpClient = new DefaultHttpClient();
            httpPost.setEntity(multipartEntity);

            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String str) {
            if (str != null) {
                Utils.copyToClipboard(MainActivity.this, str);
                Utils.showToast(MainActivity.this, getString(R.string.toast_success_copy_to_clipboard));
            } else {
                Utils.showToast(MainActivity.this, getString(R.string.toast_failed));
            }
        }
    }
}
