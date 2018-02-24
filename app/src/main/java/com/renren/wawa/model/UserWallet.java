package com.renren.wawa.model;

public class UserWallet extends BaseObject{


    /**
     * wallet : {"balance":0}
     */

    private WalletBean wallet;

    public WalletBean getWallet() {
        return wallet;
    }

    public void setWallet(WalletBean wallet) {
        this.wallet = wallet;
    }

    public static class WalletBean {
        /**
         * balance : 0
         */

        private int balance;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
}
