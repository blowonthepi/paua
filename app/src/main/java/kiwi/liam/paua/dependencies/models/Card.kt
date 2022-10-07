package kiwi.liam.paua.dependencies.models

data class Card(
    val number: String,
    val cvv: String
) {
    fun toMap(): Map<String, String> = mapOf(
        "number" to number,
        "cvv" to cvv,
    )
}
