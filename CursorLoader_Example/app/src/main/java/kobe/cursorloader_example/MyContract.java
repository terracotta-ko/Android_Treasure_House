package kobe.cursorloader_example;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by kobe on 31/08/2017.
 */

public class MyContract {
    public static final String AUTHORITY = "kobe.cursorloader_example";
    public static final String TABLE_NAME = "expense";
    public static final String COL_ID = "_id";
    public static final String COL_DATE = "cdate";
    public static final String COL_INFO = "info";
    public static final String COL_AMOUNT = "amount";
    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}
