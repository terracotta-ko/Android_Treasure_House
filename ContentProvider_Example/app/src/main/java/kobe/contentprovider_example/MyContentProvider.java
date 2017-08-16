package kobe.contentprovider_example;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MyContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_DATA = 300;
    private static final int DATA_WITH_ID = 301;

    //>> create URI rules for MyContentProvider
    static {
        sUriMatcher.addURI(
                MyContract.AUTHORITY,
                MyContract.TABLE_NAME,
                ALL_DATA
        );

        sUriMatcher.addURI(
                MyContract.AUTHORITY,
                MyContract.TABLE_NAME + "/#",   //>> ex. authority/path/3
                DATA_WITH_ID
        );
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private MySQLiteOpenHelper mMySQLiteOpenHelper = null;

    @Override
    public boolean onCreate() {
        mMySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(getContext());
        return mMySQLiteOpenHelper != null;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        switch (sUriMatcher.match(uri)) {
            case DATA_WITH_ID:
                selection = (selection == null) ? "" : " AND " + selection;
                selection = "_id = " + ContentUris.parseId(uri) + selection;
            case ALL_DATA:
                cursor = mMySQLiteOpenHelper.getReadableDatabase().query(
                        MyContract.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
        }

        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        AddItemService.addItem(getContext(), values, true);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
