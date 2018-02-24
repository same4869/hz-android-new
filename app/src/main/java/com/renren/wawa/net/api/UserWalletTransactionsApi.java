package com.renren.wawa.net.api;


import com.renren.wawa.model.UserWalletTransactions;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 获取账户流水
 */
public interface UserWalletTransactionsApi {

    @GET("wallet.transactions")
    Observable<UserWalletTransactions> getUserWalletTransactions(@Query("limit") int limit);

    @GET("wallet.transactions")
    Observable<UserWalletTransactions> getUserWalletTransactions(@Query("limit") int limit, @Query("offset") int offset);
}
