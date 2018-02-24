package com.renren.wawa.config;

public interface GameState {
    String MACHINE_STATE_IDLE = "idle";
    String MACHINE_STATE_STANDBY = "standby";
    String MACHINE_STATE_PLAYING = "playing";
    String MACHINE_STATE_CRAWLING = "crawling";
    String MACHINE_STATE_AWARDING_FAILED = "crawl.failed";
    String MACHINE_STATE_AWARDING_SUCCESS = "crawl.succeed";

    Class handle(String action);//游戏状态机切换的接口，每个状态的实例实现这个方法，如果传进来的action是一个合法的下一个状态，则可以切换成功


    //0 抓到娃娃 1 没有抓到娃娃 2 故障   3 下抓 4 游戏中
    public static final int GAME_CRAWL_SUCCEED = 0;
    public static final int GAME_CRAWL_FAILED = 1;
    public static final int GAME_CRAWL_ERROR = 2;
    public static final int GAME_CRAWLING = 3;
    public static final int GAME_CRAWL_PLAYING = 4;
}
