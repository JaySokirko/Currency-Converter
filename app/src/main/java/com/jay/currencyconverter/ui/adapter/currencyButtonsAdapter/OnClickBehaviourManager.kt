package com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter

import android.view.View
import com.jay.currencyconverter.customView.CustomMaterialButton
import com.jay.currencyconverter.ui.calculatorActivity.CalculatorActivityViewModel
import com.jay.currencyconverter.util.common.Constant.CANCEL_ALL_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CANCEL_BASE_CURRENCY_CHOICE
import com.jay.currencyconverter.util.common.Constant.CANCEL_CONVERSION_CURRENCY_CHOICE

class OnClickBehaviourManager {

    var baseButtons: MutableList<CustomMaterialButton> = mutableListOf()
    var conversionButtons: MutableList<CustomMaterialButton> = mutableListOf()

    private var baseBtn: CustomMaterialButton? = null
    private var conversionBtn: CustomMaterialButton? = null

    init {
        val subscribe = CalculatorActivityViewModel.cancelBtnClickObserver.subscribe {
            when(it) {
                CANCEL_ALL_CURRENCY_CHOICE -> {
                    resetState(conversionButtons)
                    disableButtons(conversionButtons)
                    resetState(baseButtons)
                    enableButtons(baseButtons)
                    baseBtn = null
                    conversionBtn = null
                }
                CANCEL_BASE_CURRENCY_CHOICE -> {
                    getBaseOppositeBtn()?.enable()
                    resetState(baseButtons)
                    baseBtn = null
                }
                CANCEL_CONVERSION_CURRENCY_CHOICE -> {
                    getConversionOppositeBtn()?.enable()
                    resetStateBeside(conversionButtons, getBaseOppositeBtn())
                    conversionBtn = null
                }
            }
        }
    }

    fun setup() {
        disableButtons(conversionButtons)
    }

    fun onBaseButtonClick(button: View) {
        baseBtn = button as CustomMaterialButton

        resetStateBeside(baseButtons, baseBtn)
        enableButtonsBeside(conversionButtons, conversionBtn)
        manageBtnState(baseBtn)
        onAtLeastOneBaseBtnPressed()
    }

    fun onConversionButtonClick(button: View) {
        conversionBtn = button as CustomMaterialButton

        resetStateBeside(conversionButtons, conversionBtn)
        enableButtonsBeside(baseButtons, baseBtn)
        manageBtnState(conversionBtn)
        onAtLeastOneConversionBtnPressed()
    }

    private fun onAtLeastOneConversionBtnPressed() {
        conversionBtn?.let {
            if (it.isPressedState) {
                getBaseOppositeBtn()?.disable()
                getConversionOppositeBtn()?.disable()
            } else {
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
            }
            else {
                resetState(conversionButtons)
                disableButtons(conversionButtons)
                baseBtn = null
                conversionBtn = null
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

    private fun resetStateBeside(list: List<CustomMaterialButton>,
                                 besideBtn: CustomMaterialButton?) {
        list.forEach loop@{
            if (it == besideBtn) return@loop
            it.setNotPressedState()
        }
    }

    private fun resetState(list: List<CustomMaterialButton>) {
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