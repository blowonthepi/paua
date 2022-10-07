package kiwi.liam.paua.dependencies.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route(
    @Json(name = "route_short_name") val routeShortName: String,
    @Json(name = "route_long_name") val routeLongName: String,
    @Json(name = "route_type") val routeType: TransitType
)