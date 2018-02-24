package com.renren.wawa.model;

public abstract class ShareContent extends BaseObject{
    public abstract int getShareWay();

    public abstract String getContent();

    public abstract String getTitle();

    public abstract String getURL();

    public abstract int getPictureResource();
}
