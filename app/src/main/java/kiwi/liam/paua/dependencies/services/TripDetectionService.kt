package kiwi.liam.paua.dependencies.services

import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
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
    private val transitManager: TransitManager,
    private val firestoreService: FirestoreService,
    context: Context,
) : TripDetectionService, KoinComponent, ScanCallback() {
    private val manager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val scanner: BluetoothLeScanner = manager.adapter.bluetoothLeScanner

    var numberOfNonCurrentUUID = 0

    override fun startService() {
//        val uuid = UUID.fromString("75A718F2-0000-0000-0000-000000000000")
//        val serviceUuidMaskString = "FFFFFFFF-0000-0000-0000-000000000000"
//        val parcelUuidMask = ParcelUuid.fromString(serviceUuidMaskString)
//        val filter: ScanFilter = ScanFilter.Builder()
//            .setServiceUuid(ParcelUuid(uuid), parcelUuidMask)
//            .build()
//        scanner.startScan(listOf(filter), ScanSettings.Builder().build(), this)
        scanner.startScan(this)
    }

    override fun stopService() {
        scanner.stopScan(this)
    }

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        super.onScanResult(callbackType, result)

        val buses = mapOf(
            "059ac44b-45bc-386c-908e-ab0fed155efc" to "22"
        )

        val stops = mapOf(
            "22" to listOf(
                "Wellington Station",
                "Wgtn Uni - Stop A",
            ),
        )

        val uuid = with(result.scanRecord) {
            Log.i("ScanResult", UUID.nameUUIDFromBytes(this?.bytes ?: byteArrayOf()).toString())
            UUID.nameUUIDFromBytes(this?.bytes ?: byteArrayOf())
        }.toString()

        val current = buses[uuid]
        if (current != null) {
            numberOfNonCurrentUUID = 0
            if (state.currentTrip.value?.routeId != current) {
                state.isOnTrip.value = !state.isOnTrip.value
                state.currentTrip.value = Trip(
                    routeId = current,
                    route = "Service for $current",
                    type = TransitType.Train,
                    stops = stops[current] ?: emptyList(),
                )
            }
        } else if (numberOfNonCurrentUUID > 10) {
            if (state.isOnTrip.value) {
                state.isOnTrip.value = false
                transitManager.chargeAccount(100)
                CoroutineScope(Dispatchers.IO).launch {
                    state.currentTrip.value?.let {
                        firestoreService.addTrip(it)
                    }
                    state.currentTrip.value = null
                }
            }
        } else {
            numberOfNonCurrentUUID++
        }
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