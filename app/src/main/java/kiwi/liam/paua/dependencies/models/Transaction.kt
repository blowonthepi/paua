package kiwi.liam.paua.dependencies.models

data class Transaction(
    val type: TransitType,
    val routeId: String,
    val routeName: String,
    val stops: List<String>
)