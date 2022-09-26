package kiwi.liam.paua.dependencies.services

import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import android.os.ParcelUuid
import kiwi.liam.paua.dependencies.managers.TransitManager
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.models.Trip
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

class TripDetectionState {
    var isOnTrip = MutableStateFlow(false)
    var currentTrip = MutableStateFlow<Trip?>(null)
}

interface TripDetectionService {
    fun startService()
    fun stopService()
}

class AppTripDetectionService(
    private val state: TripDetectionState,
    private val context: Context,
) : TripDetectionService, KoinComponent, AdvertiseCallback() {
    private val manager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val advertiser: BluetoothLeAdvertiser = manager.adapter.bluetoothLeAdvertiser

    override fun startService() {

        advertiser.startAdvertising(
            AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .build(),
            AdvertiseData.Builder()
                .addServiceUuid(ParcelUuid.fromString("1111"))
                .build(),
            this,
        )
    }

    override fun stopService() {
        advertiser.stopAdvertising(this)
    }

    override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
        super.onStartSuccess(settingsInEffect)
    }

}

class MockTripDetectionService(
    private val state: TripDetectionState,
    private val transitManager: TransitManager,
) : TripDetectionService {
    var mockTripJob: Job? = null

    override fun startService() {
        mockTripJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(10000L)
                state.isOnTrip.value = !state.isOnTrip.value
                if (!state.isOnTrip.value) {
                    transitManager.chargeAccount(100)
                }
            }
        }
        state.currentTrip.value = Trip(
            route = "Days Bay Ferry",
            type = TransitType.Ferry,
            stops = listOf(
                "Queens Wharf",
                "Matiu/Sommes Wharf",
                "Days Bay Wharf"
            )
        )
    }

    override fun stopService() {
        mockTripJob?.cancel()
        state.currentTrip.value = null
    }
}