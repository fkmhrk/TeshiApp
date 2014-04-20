package jp.tsur.teshiapp.utils;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Utils {

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void copyToClipboard(Context context, String str) {
        //クリップボードに格納するItemを作成
        ClipData.Item item = new ClipData.Item(str);

        //MIMETYPEの作成
        String[] mimeType = new String[1];
        mimeType[0] = ClipDescription.MIMETYPE_TEXT_URILIST;

        //クリップボードに格納するClipDataオブジェクトの作成
        ClipData cd = new ClipData(new ClipDescription("text_data", mimeType), item);

        //クリップボードにデータを格納
        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(cd);
    }

    public static File getFileByUri(Context context, Uri uri) {
        ContentResolver cr = context.getContentResolver();
        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor c = cr.query(uri, columns, null, null, null);
        assert c != null;
        c.moveToFirst();
        String fileName = c.getString(0);

        if (fileName == null) {
            return null;
        }
        File path = new File(fileName);
        if (!path.exists()) {
            return null;
        }

        return path;
    }
}
