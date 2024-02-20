package com.example.tokesttraeding

data class CurrencyInfo(
    val id: Int,
    val imageRes: Int,
    val name: String,
    val code: String,
    val price: Int
)

val currenciesList = listOf(
    CurrencyInfo(1, R.drawable.peru, "Nuevo Sol", "SOL",1),
    CurrencyInfo(2, R.drawable.eeuu, "Dolar", "DOL",2),
    CurrencyInfo(3, R.drawable.brazil, "Real", "ReB",3),
    CurrencyInfo(4, R.drawable.espana, "Euro", "EUR",4),
    CurrencyInfo(5, R.drawable.china, "Yen", "YEN",5),
    CurrencyInfo(6, R.drawable.italia, "Euro Italia", "EUI",6)
)
