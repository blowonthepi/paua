package kiwi.liam.paua.dependencies.models

data class Trip(
    val route: String,
    val type: TransitType,
    val stops: List<String>,
)