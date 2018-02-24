package com.renren.wawa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.renren.wawa.utils.BBLog;


public class UploadDatabaseHelper extends AbstractDatabaseHelper {


    private static UploadDatabaseHelper instance = null;
    private String databaseName = "same.upload.db";
    private String tag = "same_upload_database";
    private int databaseVersion = 1;
    private Context context;

    @Override
    protected String[] createDBTables() {
        String[] object = {
                "CREATE TABLE IF NOT EXISTS UPLOAD_TASK( "
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL"
                        + ",UID VARCHAR"
                        + ",FILE_NAME VARCHAR"
                        + ",COUNT VARCHAR"
                        + ")",
        };
        return object;
    }

    @Override
    protected String[] dropDBTables() {
        String[] object = {
                "DROP TABLE IF EXISTS UPLOAD_TASK"};
        return object;
    }

    @Override
    protected String getMyDatabaseName() {
        return databaseName;
    }

    @Override
    protected int getDatabaseVersion() {
        return databaseVersion;
    }

    @Override
    protected String getTag() {
        return tag;
    }

    private static synchronized void initSyn(Context context) {
        instance = new UploadDatabaseHelper(context);
    }

    public static UploadDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            initSyn(context);
        }
        return instance;
    }

    private UploadDatabaseHelper(Context context) {
        this.context = context;
    }

    public void execSQL(String sql, Object[] bindArgs) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.execSQL(sql, bindArgs);
        } catch (Exception e) {
            BBLog.w(e);
        }
    }

    public void execSQL(String[] sql, Object[][] bindArgs) {
        if (sql == null || sql.length == 0) {
            return;
        }
        init(context);
        if (mDb == null) {
            return;
        }
        for (int i = 0; i < sql.length; i++) {
            try {
                mDb.execSQL(sql[i], bindArgs[i]);
            } catch (Exception e) {
                BBLog.w(e);
            }
        }
    }

    public void execSQL(String sql) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.execSQL(sql);
        } catch (Exception e) {
            BBLog.w(e);
        }
    }

    public void update(String table, ContentValues values, String whereClause,
                       String[] whereArgs) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.update(table, values, whereClause, whereArgs);
        } catch (Exception e) {
            BBLog.w(e);
        }
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        init(context);
        if (mDb == null) {
            return null;
        }
        return mDb.rawQuery(sql, selectionArgs);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        init(context);

        if (mDb == null) {
            return null;
        }
        return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
