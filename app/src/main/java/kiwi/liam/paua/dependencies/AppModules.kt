package kiwi.liam.paua.dependencies

import com.squareup.moshi.Moshi
import kiwi.liam.paua.dependencies.managers.*
import kiwi.liam.paua.dependencies.models.LocationTypeAdapter
import kiwi.liam.paua.dependencies.models.TransitTypeAdapter
import kiwi.liam.paua.dependencies.services.*
import kiwi.liam.paua.screens.account.AccountViewModel
import kiwi.liam.paua.screens.account.views.disputeTravel.DisputeTravelViewModel
import kiwi.liam.paua.screens.auth.login.LoginViewModel
import kiwi.liam.paua.screens.auth.signup.SignupViewModel
import kiwi.liam.paua.screens.wallet.WalletViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allKoinModules
    get() = listOf(
        serviceStates,
        appServices,
        viewModels,
    )

val serviceStates = module {
    single { AuthManagerState() }
    single { TransitState() }
    single { TripDetectionState() }
}

val appServices = module {
    single<AuthManager> { AppAuthManager(get(), get()) }
    single<TransitManager> { AppTransitManager(get(), get()) }
    single<TripDetectionService> { MockTripDetectionService(get(), get(), get()) }
    single<TopologyManager> { AppTopologyManager(get()) }
    single<FirestoreService> { AppFirestoreService(get(), get()) }

    single {
        Moshi.Builder()
            .add(LocationTypeAdapter())
            .add(TransitTypeAdapter())
            .build()
    }

    single { PauaAPIService(get(), get()) }
//    single<TripDetectionService> { AppTripDetectionService(get(), get()) }
}

val viewModels = module {
    viewModel { WalletViewModel() }
    viewModel { AccountViewModel() }
    viewModel { DisputeTravelViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SignupViewModel() }
}

val mockAppServices = module {
    single<AuthManager> { MockAuthManager() }
    single<TransitManager> { MockTransitManager(get()) }
    single<TripDetectionService> { MockTripDetectionService(get(), get(), get()) }
}