package com.renren.wawa.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.wawaji.vip.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.model.ShareContent;
import com.renren.wawa.model.ShareContentText;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

public class WeChatShare {

    private static final int THUMB_SIZE = 150;

    public static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    public static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    public static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频
    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈
    public static final String WEIXIN_TIMELINE ="wx_friend_circle";
    public static final String WEIXIN_FRIEND ="wx_friend";
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;

    /**
     * 通过微信分享
     *
     * @param shareContent  分享的方式（文本、图片、链接）
     * @param shareType 分享的类型（朋友圈，会话）
     */
    public static void shareByWebchat(ShareContent shareContent, int shareType) {
        switch (shareContent.getShareWay()) {
            case WECHAT_SHARE_WAY_TEXT:
                shareText(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_PICTURE:
                sharePicture(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_WEBPAGE:
                shareWebPage(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_VIDEO:
                shareVideo(shareContent, shareType);
                break;
        }
    }



    /*
     * 获取文本分享对象
     */
    public ShareContent getShareContentText(String content, String title, String url) {
        if (mShareContentText == null) {
            mShareContentText = new ShareContentText(content,title,url);
        }
        return (ShareContentText) mShareContentText;
    }

    /**
     * 设置分享图片的内容
     *
     * @author chengcj1
     */
    public class ShareContentPicture extends ShareContent {

        private int pictureResource;

        public ShareContentPicture(int pictureResource) {
            this.pictureResource = pictureResource;
        }

        @Override
        public int getShareWay() {
            return WECHAT_SHARE_WAY_PICTURE;
        }

        @Override
        public int getPictureResource() {
            return pictureResource;
        }

        @Override
        public String getContent() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
        }

        @Override
        public String getURL() {
            return null;
        }
    }

    /*
     * 获取图片分享对象
     */
    public ShareContent getShareContentPicture(int pictureResource) {
        if (mShareContentPicture == null) {
            mShareContentPicture = new ShareContentPicture(pictureResource);
        }
        return (ShareContentPicture) mShareContentPicture;
    }

    /**
     * 设置分享链接的内容
     *
     * @author chengcj1
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private int pictureResource;

        public ShareContentWebpage(String title, String content, String url, int pictureResource) {
            this.title = title;
            this.content = content;
            this.url = url;
            this.pictureResource = pictureResource;
        }

        @Override
        public int getShareWay() {
            return WECHAT_SHARE_WAY_WEBPAGE;
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
            return pictureResource;
        }
    }

    /*
     * 获取网页分享对象
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, int pictureResource) {
        if (mShareContentWebpag == null) {
            mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        }
        return (ShareContentWebpage) mShareContentWebpag;
    }

    /**
     * 设置分享视频的内容
     *
     * @author chengcj1
     */
    public class ShareContentVideo extends ShareContent {
        private String url;

        public ShareContentVideo(String url) {
            this.url = url;
        }

        @Override
        public int getShareWay() {
            return WECHAT_SHARE_WAY_VIDEO;
        }

        @Override
        public String getContent() {
            return null;
        }

        @Override
        public String getTitle() {
            return null;
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

    /*
     * 获取视频分享内容
     */
    public ShareContent getShareContentVideo(String url) {
        if (mShareContentVideo == null) {
            mShareContentVideo = new ShareContentVideo(url);
        }
        return (ShareContentVideo) mShareContentVideo;
    }

    /*
     * 分享文字
     */
    private static void shareText(ShareContent shareContent, int shareType) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(WawaApplication.getContext().getResources(), R.mipmap.ic_launcher);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        WawaApplication.getInstance().getmWxApi().sendReq(req);
    }

    /*
     * 分享图片
     */
    private static void sharePicture(ShareContent shareContent, int shareType) {
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
//        WXImageObject imgObj = new WXImageObject(bitmap);
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imgObj;
//
//        Bitmap thumbBitmap = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
//        bitmap.recycle();
//        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  //设置缩略图
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("imgshareappdata");
//        req.message = msg;
//        req.scene = shareType;
//        SameApplication.getApplication().getmWxApi().sendReq(req);
    }

    /*
     * 分享链接
     */
    private static void shareWebPage(ShareContent shareContent, int shareType) {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = shareContent.getURL();
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = shareContent.getTitle();
//        msg.description = shareContent.getContent();
//
//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
//        if (thumb == null) {
//            Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
//        } else {
//            msg.thumbData = Util.bmpToByteArray(thumb, true);
//        }
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = shareType;
//        SameApplication.getApplication().getmWxApi().sendReq(req);
    }

    /*
     * 分享视频
     */
    private static void shareVideo(ShareContent shareContent, int shareType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(WawaApplication.getContext().getResources(), R.mipmap.ic_launcher);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = shareType;
        WawaApplication.getInstance().getmWxApi().sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
