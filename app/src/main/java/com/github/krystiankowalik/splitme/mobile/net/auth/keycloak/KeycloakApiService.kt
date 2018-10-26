package com.github.krystiankowalik.splitme.mobile.net.auth.keycloak

import com.github.krystiankowalik.splitme.mobile.model.auth.TokenResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KeycloakApiService {

    @POST("auth/realms/SplitMe/protocol/openid-connect/token")
    @FormUrlEncoded
    fun exchangeToken(
            @Field("client_id") clientId: String,
            @Field("grant_type") grantType: String,
            @Field("subject_token") subjectToken: String,
            @Field("subject_token_type") subjectTokenType: String
    ): Observable<TokenResponse>


}
