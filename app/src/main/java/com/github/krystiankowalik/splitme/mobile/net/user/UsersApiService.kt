package com.github.krystiankowalik.splitme.mobile.net.user

import com.github.krystiankowalik.splitme.mobile.model.user.User
import io.reactivex.Observable
import retrofit2.http.GET

interface UsersApiService {

    @get:GET("users/")
    val allUsers: Observable<List<User>>

   /* @GET("users/{id}")
    fun getUserById(@Path("id") id: Int) : Observable<User>

    @GET("users/{id}/transactions")
    fun getUsersTransactions(@Path("id") id: Int): Observable<List<Transaction>>

    @GET("groups/byUserId/")
    fun getUsersGroups(@Query("userId") userId: Int): Observable<List<Group>>

    @GET("transactions/{id}")
    fun getTransactionById(@Path("id") transactionId: Int): Observable<Transaction>

    @POST("transactions")
    fun addTransaction(@Body transaction: Transaction): Observable<Transaction>

    @PUT("transactions/{id}")
    fun updateTransaction(@Path("id") transactionId: Int, @Body transaction: Transaction): Observable<Transaction>*/


}
