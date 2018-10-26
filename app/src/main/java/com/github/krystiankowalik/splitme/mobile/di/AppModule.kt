package com.github.krystiankowalik.splitme.mobile.di

import android.content.Context
import com.github.krystiankowalik.authenticationapp.R
import com.github.krystiankowalik.splitme.mobile.net.auth.keycloak.KeycloakApiClient
import com.github.krystiankowalik.splitme.mobile.net.user.UsersApiClient
import com.github.krystiankowalik.splitme.mobile.util.SharedPreferencesManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.common.eventbus.EventBus
import com.google.gson.*
import dagger.Provides
import net.sourceforge.jeval.Evaluator
import org.joda.time.LocalDate
import javax.inject.Singleton

@dagger.Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context = context


    @Provides
    @Singleton
    fun providesGoogleSignInOptions(context: Context) =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.server_client_id))
                    .requestEmail()
                    .build()

    @Provides
    @Singleton
    fun providesGoogleSignInClient(googleSignInOptions: GoogleSignInOptions) =
            GoogleSignIn.getClient(context, googleSignInOptions)

    @Provides
    @Singleton
    fun sharedPreferencesManager(context: Context): SharedPreferencesManager =
            SharedPreferencesManager(context)

    @Provides
    @Singleton
    fun providesEventBus(): EventBus = EventBus()


    @Provides
    @Singleton
    fun providesJEvalEvaluator() = Evaluator()

    @Provides
    @Singleton
    fun providesUserApiClient() = UsersApiClient()

    @Provides
    @Singleton
    fun providesKeycloakApiClient() = KeycloakApiClient()

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ -> LocalDate(json.asString) })
                .registerTypeAdapter(LocalDate::class.java, JsonSerializer<LocalDate> { src, _, _ ->
                    val `object` = JsonObject()
                    `object`.addProperty("date", src.toString("yyyy-MM-dd"))
                    `object`.getAsJsonPrimitive("date")
                })
                .create()
    }


}
