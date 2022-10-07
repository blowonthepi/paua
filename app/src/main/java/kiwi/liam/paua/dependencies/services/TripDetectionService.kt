package kiwi.liam.paua.dependencies.services

import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.dependencies.managers.TransitManager
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.models.Trip
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import java.util.*

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
) : TripDetectionService, KoinComponent, ScanCallback() {
    private val manager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val advertiser: BluetoothLeScanner = manager.adapter.bluetoothLeScanner

    override fun startService() {
        val uuid = UUID.fromString("75a718f2-0a7d-476b-8c3c-b9c9f38559f3")
        val serviceUuidMaskString = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF"
        val parcelUuidMask = ParcelUuid.fromString(serviceUuidMaskString)
        val filter: ScanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(uuid), parcelUuidMask)
            .build()
        advertiser.startScan(listOf(filter), ScanSettings.Builder().build(), this)
    }

    override fun stopService() {
        advertiser.stopScan(this)
    }

    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        Log.i("ScanResult", result?.advertisingSid?.toString() ?: "NONE")

    }

    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)
        Log.e("ScanFailed", "Ble scan failed")
    }

}

class MockTripDetectionService(
    private val state: TripDetectionState,
    private val transitManager: TransitManager,
    private val firestoreService: FirestoreService,
) : TripDetectionService {
    var mockTripJob: Job? = null

    var isServiceRunning by mutableStateOf(false)

    val availableTrips = listOf(
        Trip(
            routeId = "WHF",
            route = "Days Bay Ferry",
            type = TransitType.Ferry,
            stops = listOf(
                "Queens Wharf",
                "Matiu/Sommes Wharf",
                "Days Bay Wharf"
            )
        ),
        Trip(
            routeId = "HVL",
            route = "Hutt Valley Line",
            type = TransitType.Train,
            stops = listOf(
                "Wellington Station",
                "Upper Hutt Station",
            )
        ),
        Trip(
            routeId = "22",
            route = "Wellington Station",
            type = TransitType.Bus,
            stops = listOf(
                "Wgtn Uni - Stop B",
                "Wellington Station - Stop D",
            )
        )
    )

    var selectedTrip by mutableStateOf(availableTrips[0])

    override fun startService() {
        if (isServiceRunning) return

        isServiceRunning = true
        state.currentTrip.value = selectedTrip
        state.isOnTrip.value = true
        mockTripJob = CoroutineScope(Dispatchers.IO).launch {
            delay(10000L)
            stopService()
            transitManager.chargeAccount(100)
            firestoreService.addTrip(selectedTrip)
        }
    }

    override fun stopService() {
        isServiceRunning = false
        mockTripJob?.cancel()
        state.currentTrip.value = null
        state.isOnTrip.value = false
    }
}