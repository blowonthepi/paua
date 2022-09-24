package kiwi.liam.paua.dependencies

import kiwi.liam.paua.dependencies.managers.*
import kiwi.liam.paua.dependencies.services.MockTripDetectionService
import kiwi.liam.paua.dependencies.services.TripDetectionService
import kiwi.liam.paua.dependencies.services.TripDetectionState
import kiwi.liam.paua.routers.AppRouter
import kiwi.liam.paua.routers.TabRouter
import kiwi.liam.paua.screens.wallet.WalletViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceStates = module {
    single { AuthManagerState() }
    single { TransitState() }
    single { TripDetectionState() }
}

val appServices = module {
    single<AuthManager> { AppAuthManager(get()) }
    single<TransitManager> { AppTransitManager(get()) }
    single<TripDetectionService> { MockTripDetectionService(get()) }
//    single<TripDetectionService> { AppTripDetectionService(get()) }
}

val appRouters = module {
    single { AppRouter() }
    single { TabRouter() }
}

val viewModels = module {
    viewModel { WalletViewModel() }
}

val mockAppServices = module {
    single<AuthManager> { MockAuthManager() }
    single<TransitManager> { MockTransitManager() }
    single<TripDetectionService> { MockTripDetectionService(get()) }
}