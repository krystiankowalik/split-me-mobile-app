package com.github.krystiankowalik.splitme.mobile.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.krystiankowalik.authenticationapp.R
import com.github.krystiankowalik.splitme.mobile.App
import com.github.krystiankowalik.splitme.mobile.net.auth.keycloak.KeycloakApiClient
import com.github.krystiankowalik.splitme.mobile.util.SharedPreferencesManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var keycloakApiClient: KeycloakApiClient

    val RC_SIGN_IN = 132

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)

        sign_in_button.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        silentSignIn()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> {
                signOut()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            updateUI(null)
        }
    }

    private fun silentSignIn() {
        googleSignInClient
                .silentSignIn()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        handleSignInResult(task)
                    } else {
                        Toast.makeText(this, "Couldn't sign in silently. Try manually.", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
//            updateUI(task.getResult(ApiException::class.java))
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            account
                    .idToken
                    .toString()
                    .let {
                        updateUI(it)
                        Timber.e(it)
                        sharedPreferencesManager.write(getString(R.string.google_id_token), it)
                    }

            keycloakApiClient
                    .service
                    .exchangeToken(
                            clientId = getString(R.string.keycloak_mobile_client_id),
                            grantType = getString(R.string.keycloak_mobile_grant_type),
                            subjectToken = sharedPreferencesManager.read(getString(R.string.google_id_token)),
                            subjectTokenType = getString(R.string.keycloak_mobile_subject_token_type)
                    )
                    .subscribeOn(Schedulers.io())
                    .subscribe({ tokenResponse ->
                        tokenResponse.accessToken.let {
                            sharedPreferencesManager.write(getString(R.string.keycloak_access_token), it)
                            Timber.e("The token is: $it")
                        }
                    }, { error ->
                        Timber.e("There was an error exchanging token")
                        Timber.e(error)
                    }
                    )

            startActivity(Intent(this, SampleActivity::class.java))

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //            Log.w(FragmentActivity.TAG, "signInResult:failed code=" + e.statusCode)
            Timber.e("I'm here signInResult:failed code= + ${e.statusCode}")
            updateUI(null)
        }
    }

    private fun updateUI(text: String?) {
        status_text.text = text ?: "Signed out"
    }


}
