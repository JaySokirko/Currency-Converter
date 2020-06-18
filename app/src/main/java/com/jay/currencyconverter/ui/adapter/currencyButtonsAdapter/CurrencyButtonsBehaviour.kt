package com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter

import android.view.View
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivityViewModel
import com.jay.currencyconverter.util.common.Constant.CANCEL_ALL_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CANCEL_BASE_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CANCEL_CONVERSION_CURRENCY_CHOICE
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class CurrencyButtonsBehaviour {

    var baseButtons: MutableList<CustomMaterialButton> = mutableListOf()
    var conversionButtons: MutableList<CustomMaterialButton> = mutableListOf()
    var isConversionButtonsShouldBeEnabled = false

    private val disposable = CompositeDisposable()
    private var baseBtn: CustomMaterialButton? = null
    private var conversionBtn: CustomMaterialButton? = null

    init {
        val subscribe: Disposable = CalculatorActivityViewModel.cancelBtnClickObserver.subscribe {
            when (it) {
                CANCEL_ALL_CURRENCY_CHOICE -> {
                    setNoPressedState(conversionButtons)
                    disableButtons(conversionButtons)
                    setNoPressedState(baseButtons)
                    enableButtons(baseButtons)
                    baseBtn = null
                    conversionBtn = null
                }
                CANCEL_BASE_CURRENCY_CHOICE -> {
                    enableButtons(baseButtons)
                    setNoPressedStateBeside(baseButtons, getConversionOppositeBtn())
                    getConversionOppositeBtn()?.disable()
                    getBaseOppositeBtn()?.enable()
                    baseBtn = null
                }
                CANCEL_CONVERSION_CURRENCY_CHOICE -> {
                    enableButtons(conversionButtons)
                    setNoPressedStateBeside(conversionButtons, getBaseOppositeBtn())
                    getConversionOppositeBtn()?.enable()
                    getBaseOppositeBtn()?.disable()
                    conversionBtn = null
                }
            }
        }
        disposable.add(subscribe)
    }

    fun setup() {
        if (!isConversionButtonsShouldBeEnabled) disableButtons(conversionButtons)
    }

    fun onBaseButtonClick(button: View) {
        baseBtn = button as CustomMaterialButton

        setNoPressedStateBeside(baseButtons, baseBtn)
        enableButtonsBeside(conversionButtons, conversionBtn)
        manageBtnState(baseBtn)
        onAtLeastOneBaseBtnPressed()
    }

    fun onConversionButtonClick(button: View) {
        conversionBtn = button as CustomMaterialButton

        setNoPressedStateBeside(conversionButtons, conversionBtn)
        enableButtonsBeside(baseButtons, baseBtn)
        manageBtnState(conversionBtn)
        onAtLeastOneConversionBtnPressed()
    }

    fun onDestroy() {
        disposable.clear()
    }

    private fun onAtLeastOneConversionBtnPressed() {
        conversionBtn?.let {
            if (it.isPressedState) {
                getBaseOppositeBtn()?.disable()
                getConversionOppositeBtn()?.disable()
            }
            else {
                getBaseOppositeBtn()?.disable()
                getConversionOppositeBtn()?.enable()
                conversionBtn = null
            }
        }
    }

    private fun onAtLeastOneBaseBtnPressed() {
        baseBtn?.let {
            if (it.isPressedState) {
                enableButtonsBeside(conversionButtons, conversionBtn)
                getBaseOppositeBtn()?.disable()
                getConversionOppositeBtn()?.disable()
                isConversionButtonsShouldBeEnabled = true
            }
            else {
                setNoPressedState(conversionButtons)
                disableButtons(conversionButtons)
                baseBtn = null
                conversionBtn = null
                isConversionButtonsShouldBeEnabled = false
            }
        }
    }

    private fun manageBtnState(button: CustomMaterialButton?) {
        button ?: return
        if (button.isPressedState) {
            button.setNotPressedState()
        }
        else {
            button.setPressedState()
        }
    }

    private fun getBaseOppositeBtn(): CustomMaterialButton? {
        return conversionButtons.find { it.text == baseBtn?.text }
    }

    private fun getConversionOppositeBtn(): CustomMaterialButton? {
        return baseButtons.find { it.text == conversionBtn?.text }
    }

    private fun setNoPressedStateBeside(list: List<CustomMaterialButton>,
                                        besideBtn: CustomMaterialButton?) {
        list.forEach loop@{
            if (it == besideBtn) return@loop
            it.setNotPressedState()
        }
    }

    private fun setNoPressedState(list: List<CustomMaterialButton>) {
        list.forEach { it.setNotPressedState() }
    }

    private fun enableButtonsBeside(list: List<CustomMaterialButton>,
                                    besideBtn: CustomMaterialButton?) {
        list.forEach loop@{
            if (it == besideBtn) return@loop
            it.enable()
        }
    }

    private fun disableButtons(list: List<CustomMaterialButton>) = list.forEach { it.disable() }

    private fun enableButtons(list: List<CustomMaterialButton>) = list.forEach { it.enable() }
}