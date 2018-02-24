package com.renren.wawa.model;


public class ConfigResult extends BaseObject {

    private GameConfig app;

    private User user;

    private Wallet wallet;


    public GameConfig getApp() {
        return app;
    }

    public void setApp(GameConfig app) {
        this.app = app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "ConfigResult{" +
                "app=" + app +
                ", user=" + user +
                ", wallet=" + wallet +
                '}';
    }
}
