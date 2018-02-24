package com.renren.wawa.config;

public interface GameControlStatus {
    // 0 下爪 1 前-开始 2 前-结束 3 后-开始 4 后-结束 5 左-开始 6 左-结束 7 右-开始 8 右-结束

    public final static int CRAWLING = 0;


    public final static int FORWARD_START = 1;
    public final static int FORWARD_END = 2;

    public final static int BACKWARD_START = 3;
    public final static int BACKWARD_END = 4;


    public final static int LEFT_START = 5;
    public final static int LEFT_END = 6;

    public final static int RIGHT_START = 7;
    public final static int RIGHT_END = 8;
}
