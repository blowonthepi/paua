package kiwi.liam.paua.dependencies.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

enum class TransitType(val type: Int) {
    Train(2),
    Bus(3),
    Ferry(4),
    CableCar(5),
    Other(-1),
}

class TransitTypeAdapter {
    @ToJson
    fun toJson(transitType: TransitType): Int {
        return transitType.type
    }

    @FromJson
    fun fromJson(type: Int): TransitType {
        return TransitType.values().firstOrNull { it.type == type } ?: TransitType.Other
    }
}