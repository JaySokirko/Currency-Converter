package com.jay.currencyconverter.ui.adapter.currencyButtonsAdapter

import android.view.View
import com.jay.currencyconverter.customView.CustomMaterialButton

class OnClickBehaviourManager {

    var baseButtons: MutableList<CustomMaterialButton> = mutableListOf()
    var conversionButtons: MutableList<CustomMaterialButton> = mutableListOf()

    fun setup() { disableButtons(conversionButtons) }

    fun onBaseButtonClick(button: View) {
        button as CustomMaterialButton

        resetStateBeside(baseButtons, button)
        resetVisibility(conversionButtons)
        managePressedBtn(button)
        manageOppositeBtn(button, isBaseBtn = true)
        checkBaseBtnState()
    }

    fun onConversionButtonClick(button: View) {
        button as CustomMaterialButton

        resetStateBeside(conversionButtons, button)
        resetVisibility(baseButtons)
        managePressedBtn(button)
        manageOppositeBtn(button, isBaseBtn = false)
    }

    private fun manageOppositeBtn(clickedBtn: CustomMaterialButton, isBaseBtn: Boolean) {
        val oppositeBtn: CustomMaterialButton? = if (isBaseBtn) {
            conversionButtons.find { clickedBtn.text == it.text }
        } else {
            baseButtons.find { clickedBtn.text == it.text }
        }

        oppositeBtn?.let {
            if (clickedBtn.isPressedState){
                it.visibility = View.INVISIBLE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }

    private fun managePressedBtn(currentClickedBtn: CustomMaterialButton) {
        if (currentClickedBtn.isPressedState) {
            currentClickedBtn.setNotPressedState()
        } else {
            currentClickedBtn.setPressedState()
        }
    }

    private fun checkBaseBtnState() {
        val atLeastOneBtnPressed: CustomMaterialButton? = baseButtons.find { it.isPressedState }
        if (atLeastOneBtnPressed == null) {
            disableButtons(conversionButtons)
            resetVisibility(baseButtons)
        } else {
            enableButtons(conversionButtons)
        }
    }

    private fun disableButtons(list: List<CustomMaterialButton>) {
        list.forEach { it.disable() }
    }

    private fun enableButtons(list: List<CustomMaterialButton>) {
        list.forEach { it.enable() }
    }

    private fun resetStateBeside(
        list: List<CustomMaterialButton>,
        besideBtn: CustomMaterialButton
    ) {
        list.forEach loop@{
            if (it == besideBtn) return@loop
            it.setNotPressedState()
        }
    }

    private fun resetVisibility(list: List<CustomMaterialButton>){
        list.forEach { it.visibility = View.VISIBLE }
    }

}