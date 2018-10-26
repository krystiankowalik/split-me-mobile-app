package com.github.krystiankowalik.splitme.mobile.model.auth

import com.google.gson.annotations.SerializedName

data class TokenResponse(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("refresh_token")
        val refreshToken: String
)