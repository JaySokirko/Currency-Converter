package com.jay.currencyconverter.ui

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jay.currencyconverter.BaseApplication
import com.jay.currencyconverter.R
import com.jay.currencyconverter.util.DateManager
import com.jay.currencyconverter.util.roundToFloat
import java.lang.IllegalArgumentException

class LineChartSetup(
    private val lineChart: LineChart,
    private val exchangeRateList: MutableList<Double>
) {

    private val baseContext: Context = BaseApplication.baseComponent.application.baseContext
    private val xAxis: XAxis = lineChart.xAxis
    private val yAxis: YAxis = lineChart.axisLeft
    private val lineDataSet = LineDataSet(dataValues(), null)

    init {
        setupLineChart()
        setupXAxis()
        setupYAxis()
        setupLineData()

        val dataSet: MutableList<ILineDataSet> = mutableListOf()
        dataSet.add(lineDataSet)
        lineChart.data = LineData(dataSet)
    }

    private fun setupLineChart() {
        lineChart.apply {
            axisRight.isEnabled = false
            description = null
            legend.isEnabled = false
            animateX(2000, Easing.Linear)
        }
    }

    private fun setupXAxis() {
        xAxis.apply {
            valueFormatter = XAxisValueFormatter()
            position = XAxis.XAxisPosition.TOP
            labelCount = dataValues().size - 1
            setDrawLabels(true)
            setDrawAxisLine(true)
            setDrawGridLines(true)
            enableGridDashedLine(10f, 10f, 10f)
        }
    }

    private fun setupYAxis() {
        yAxis.apply {
            setDrawLabels(false); // no axis labels
            setDrawAxisLine(false); // no axis line
            setDrawGridLines(false); // no grid lines
        }
    }

    private fun setupLineData() {
        lineDataSet.apply {
            valueTextSize = 10f
            setDrawFilled(true)
            fillDrawable = baseContext.resources.getDrawable(R.drawable.gradient)
            lineWidth = 1f
            color = Color.BLUE
            setCircleColor(Color.BLUE)
        }
    }

    private fun dataValues(): List<Entry> {
        if (exchangeRateList.size > 5) {
            throw IllegalArgumentException("max 5 values")
        }
        val list: MutableList<Entry> = mutableListOf()
        list.add(Entry(0f, exchangeRateList[4].roundToFloat()))
        list.add(Entry(1f, exchangeRateList[3].roundToFloat()))
        list.add(Entry(2f, exchangeRateList[2].roundToFloat()))
        list.add(Entry(3f, exchangeRateList[1].roundToFloat()))
        list.add(Entry(4f, exchangeRateList[0].roundToFloat()))
        return list
    }

    class XAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return when (value) {
                0f -> DateManager.dayNameList[4]
                1f -> DateManager.dayNameList[3]
                2f -> DateManager.dayNameList[2]
                3f -> DateManager.dayNameList[1]
                4f -> DateManager.dayNameList[0]
                else -> throw IllegalArgumentException("Only the following values are available: " +
                        "0,1,2,3,4")
            }
        }
    }
}