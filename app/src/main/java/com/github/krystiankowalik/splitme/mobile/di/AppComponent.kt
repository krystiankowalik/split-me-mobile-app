package com.github.krystiankowalik.splitme.mobile.di

import com.github.krystiankowalik.splitme.mobile.net.auth.keycloak.KeycloakApiClient
import com.github.krystiankowalik.splitme.mobile.net.user.UsersApiClient
import com.github.krystiankowalik.splitme.mobile.ui.LoginActivity
import com.github.krystiankowalik.splitme.mobile.ui.SampleActivity
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(usersApiClient: UsersApiClient)

    fun inject(sampleActivity: SampleActivity)

    fun inject(keycloakApiClient: KeycloakApiClient)

    /* fun inject(dashboardActivity: DashboardActivity)

     fun inject(transactionsFragment: TransactionsFragment)
     //fun inject(payablesFragment: PayablesFragment)
     fun inject(transactionDetailFragment: TransactionDetailFragment)

     fun inject(userSearchFragment: UserSearchFragment)

     fun inject(groupsFragment: GroupsFragment)

     fun inject(transactionSelectionAction: TransactionSelectionAction)

     fun inject(apiClient: ApiClient)

     fun inject(calculatorDialogFragment: CalculatorDialogFragment)*/
    /*void inject(LoginActivity loginActivity);

    void inject(DashboardActivity mainActivity);

    void inject(ContactsFragment contactsFragment);*/


}
