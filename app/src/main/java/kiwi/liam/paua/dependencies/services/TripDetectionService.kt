package kiwi.liam.paua.dependencies.services

import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.models.Trip
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class TripDetectionState {
    var isOnTrip = MutableStateFlow(false)
    var currentTrip = MutableStateFlow<Trip?>(null)
}

interface TripDetectionService {
    fun startService()
    fun stopService()
}

class AppTripDetectionService(private val state: TripDetectionState) : TripDetectionService {
    override fun startService() {
        // TODO
    }

    override fun stopService() {
        // TODO
    }
}

class MockTripDetectionService(private val state: TripDetectionState) : TripDetectionService {
    var mockTripJob: Job? = null

    override fun startService() {
        mockTripJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(10000L)
                state.isOnTrip.value = !state.isOnTrip.value
            }
        }
        state.currentTrip.value = Trip(
            route = "Hutt Valley Line",
            type = TransitType.Train,
        )
    }

    override fun stopService() {
        mockTripJob?.cancel()
        state.currentTrip.value = null
    }
}