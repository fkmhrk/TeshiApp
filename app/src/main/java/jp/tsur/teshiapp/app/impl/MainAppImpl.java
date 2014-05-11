package jp.tsur.teshiapp.app.impl;

import android.os.AsyncTask;

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

import jp.tsur.teshiapp.app.MainApp;

/**
 * Implementation of {@link jp.tsur.teshiapp.app.MainApp}
 */
public class MainAppImpl implements MainApp {

    @Override
    public void upload(File file, final String url, final UploadListener callback) {
        new AsyncTask<File, Void, String>() {
            private Exception e;
            @Override
            protected String doInBackground(File... files) {
                // ポスト先のURLを指定
                HttpPost httpPost = new HttpPost(url);
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
                    this.e = e;
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String str) {
                if (str != null) {
                    callback.onSuccess(str);
                } else {
                    callback.onError(e);
                }
            }
        }.execute(file);
    }
}
