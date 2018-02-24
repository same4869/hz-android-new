package com.renren.wawa.db;


import android.content.Context;
import android.database.Cursor;

import com.renren.wawa.model.UploadInfo;
import com.renren.wawa.utils.BBLog;

import java.util.ArrayList;
import java.util.List;


public class UploadInfoDBHelper {

    private static final String TABLE_NAME = "UPLOAD_TASK";
    private static final int MAX_SAVE_TIME = 5;
    private volatile static UploadInfoDBHelper mInstance;
    private UploadDatabaseHelper mHelper;
    private int saveTime;

    private UploadInfoDBHelper(Context context) {
        mHelper = UploadDatabaseHelper.getInstance(context);
    }

    public static UploadInfoDBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UploadInfoDBHelper.class) {
                if (mInstance == null) {
                    mInstance = new UploadInfoDBHelper(context);
                }
            }
        }
        return mInstance;
    }

    public String getTable() {
        return TABLE_NAME;
    }


    public void save(UploadInfo obj) {
        if (obj == null) {
            return;
        }
        String sql = "insert into " + getTable() + "("
                + "UID,FILE_NAME,COUNT"
                + ")values(?,?,?)";
        Object[] bindArgs = {obj.getUserId(), obj.getFileName(), obj.getCount()};
        BBLog.e(TABLE_NAME, "obj.getFileName " + obj.getFileName());
        mHelper.execSQL(sql, bindArgs);
    }

    public void delete(String fileName) {
        if (fileName == null) {
            return;
        }
        String sql = "delete from " + getTable() + " where FILE_NAME = \""
                + fileName + "\"";
        mHelper.execSQL(sql);
    }


    public void deleteAll(String uid) {
        String sql = "delete from " + getTable() + " where uid = \"" + uid
                + "\"";
        mHelper.execSQL(sql);
    }


    public List<UploadInfo> getAllData(long uid) {

        String selection = null;
        String[] selectionArgs = null;
        String userName = String.valueOf(uid);
        if (userName != null) {
            selection = "UID = ?";
            selectionArgs = new String[]{userName};
        }
        String[] columns = { "FILE_NAME","COUNT"};
        List<UploadInfo> uploadInfoList = new ArrayList<UploadInfo>();
        Cursor cursor = null;
        try {
            cursor = mHelper.query(getTable(), columns, selection,
                    selectionArgs, null, null, null);
            while (cursor.moveToNext()) {
                String fileName = cursor.getString(0);
                int count  = cursor.getInt(1);
                UploadInfo uploadInfo = new UploadInfo(fileName,count,uid);
                BBLog.e("uploadInfo "+uploadInfo.toString());
                uploadInfoList.add(uploadInfo);
            }
        } catch (Exception e) {
            BBLog.w(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return uploadInfoList;
    }


    public Boolean isContain(String fileName) {
        BBLog.e(TABLE_NAME, "fileName " + fileName);

        int count = 0;
        String str = "select count(*)  from " + getTable() + " where FILE_NAME = \"" + fileName + "\"";

        Cursor cursor = null;
        try {
            cursor = mHelper.rawQuery(str, null);
            if (cursor.moveToNext()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            BBLog.w(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count > 0;
    }


}
