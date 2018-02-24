package com.renren.wawa.model;

public class UploadInfo extends BaseObject{
    private String fileName;
    private int count;
    private long userId;

    public UploadInfo(String fileName, int count, long userId) {
        this.fileName = fileName;
        this.count = count;
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "fileName='" + fileName + '\'' +
                ", count=" + count +
                ", userId=" + userId +
                '}';
    }
}
