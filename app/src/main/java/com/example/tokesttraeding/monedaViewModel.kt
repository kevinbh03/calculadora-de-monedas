package com.example.tokesttraeding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MonedaViewModel : ViewModel() {
    private val _selectedCurrency1 = MutableStateFlow<CurrencyInfo?>(null)
    val selectedCurrency1: StateFlow<CurrencyInfo?> =
        _selectedCurrency1.asStateFlow()

    private val _selectedCurrency2 = MutableStateFlow<CurrencyInfo?>(null)
    val selectedCurrency2: StateFlow<CurrencyInfo?> =
        _selectedCurrency2.asStateFlow()

    private val _textState1 = MutableStateFlow("")
    val textState1: StateFlow<String> = _textState1.asStateFlow()

    private val _textState2 = MutableStateFlow("")
    val textState2: StateFlow<String> = _textState2.asStateFlow()

    fun updateTextState1(newValue: String) {
        _textState1.value = newValue
    }

    fun updateTextState2(newValue: String) {
        _textState2.value = newValue
    }

    fun selectCurrency(currency: CurrencyInfo, forFirstFlag: Boolean) {
        if (forFirstFlag) {
            _selectedCurrency1.value = currency
        } else {
            _selectedCurrency2.value = currency
        }
    }

    fun selectText(newValue: String, forFirstFlag: Boolean) {
        if (forFirstFlag) {
            updateTextState1(newValue)
        } else {
            updateTextState2(newValue)
        }
    }

    fun clearTextFields() {
        _textState1.value = ""
        _textState2.value = ""
    }

    fun swapValues() {
        if (_selectedCurrency1.value == null && _selectedCurrency2.value == null) {
            _selectedCurrency1.value = CurrencyInfo(1, R.drawable.peru, "Nuevo Sol", "SOL",1)
            _selectedCurrency2.value = CurrencyInfo(2, R.drawable.eeuu, "Dolar", "DOL",2)
        }
        if (_selectedCurrency1.value == null) {
            _selectedCurrency1.value = CurrencyInfo(1, R.drawable.peru, "Nuevo Sol", "SOL",1)
        }
        if (_selectedCurrency2.value == null) {
            _selectedCurrency2.value = CurrencyInfo(2, R.drawable.eeuu, "Dolar", "DOL",2)
        }
            val val1 = _textState1.value
            val val2 = _textState2.value
            val currency1 = _selectedCurrency1.value
            val currency2 = _selectedCurrency2.value
            _selectedCurrency1.value = currency2
            _selectedCurrency2.value = currency1
            _textState1.value = val2
            _textState2.value = val1
    }

    fun convertCurrency() {
        val currency1 = _selectedCurrency1.value
        val currency2 = _selectedCurrency2.value
        val amount = _textState1.value.toFloatOrNull()

        if (currency1 != null && currency2 != null && amount != null) {
            val result = (amount * currency2.price) / currency1.price
            _textState2.value = result.toString()
        }
    }


}
