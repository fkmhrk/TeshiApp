package jp.tsur.teshiapp.app;

import java.io.File;

/**
 * Logic for {@link jp.tsur.teshiapp.MainActivity}
 */
public interface MainApp {
    public interface UploadListener {
        void onSuccess(String body);

        void onError(Exception e);
    }

    /**
     * Uploads the file
     * @param file the file
     * @param url the url
     * @param callback callback
     */
    void upload(File file, String url, UploadListener callback);
}
