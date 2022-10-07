package kiwi.liam.paua.dependencies.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
data class Stop(
    @Json(name = "stop_id") val stopId: String,
    @Json(name = "stop_name") val stopName: String,
    @Json(name = "stop_desc") val stopDesc: String,
    @Json(name = "zone_id") val zoneId: String,
    @Json(name = "stop_lat") val stopLat: Double,
    @Json(name = "stop_lon") val stopLon: Double,
    @Json(name = "location_type") val locationType: LocationType,
    @Json(name = "parent_station") val parentStation: String,
)

enum class LocationType {
    Stop,
    Station,
    EntranceExit,
    Generic,
    Boarding,
}

class LocationTypeAdapter {
    @ToJson
    fun toJson(locationType: LocationType): Int {
        return locationType.ordinal
    }

    @FromJson
    fun fromJson(ordinal: Int): LocationType {
        return LocationType.values()[ordinal]
    }
}