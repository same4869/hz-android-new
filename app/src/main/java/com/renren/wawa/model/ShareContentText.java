package com.renren.wawa.model;

import static com.renren.wawa.utils.WeChatShare.WECHAT_SHARE_WAY_TEXT;

public class ShareContentText extends ShareContent {
    private String content;
    private String title;
    private String url;

    /**
     * 构造分享文字类
     *
     * @param content 分享的文字内容
     */
    public ShareContentText(String content, String title, String url) {
        this.content = content;
        this.title = title;
        this.url = url;
    }

    @Override
    public int getShareWay() {
        return WECHAT_SHARE_WAY_TEXT;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public int getPictureResource() {
        return -1;
    }
}
