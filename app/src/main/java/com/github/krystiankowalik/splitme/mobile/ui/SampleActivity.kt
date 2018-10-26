package com.github.krystiankowalik.splitme.mobile.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.krystiankowalik.authenticationapp.R
import com.github.krystiankowalik.splitme.mobile.App
import com.github.krystiankowalik.splitme.mobile.net.user.UsersApiClient
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class SampleActivity : AppCompatActivity() {

    @Inject
    lateinit var usersApiClient: UsersApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)

        usersApiClient.service
                .allUsers
                .subscribeOn(Schedulers.io())
                .subscribe({ users ->
                    Timber.e(users.toString())
                }, { error ->
                    Timber.e(error.message)
                })

    }


}
