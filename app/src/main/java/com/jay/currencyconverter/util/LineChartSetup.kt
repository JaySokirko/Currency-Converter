package com.jay.currencyconverter.util

import android.content.Context
import android.graphics.Color
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

class LineChartSetup(private val lineChart: LineChart) {

    private val baseContext: Context = BaseApplication.baseComponent.application.baseContext

    init {
        lineChart.setNoDataText("No Data")
        lineChart.axisRight.isEnabled = false
        lineChart.description = null
        lineChart.legend.isEnabled = false

        val right: XAxis = lineChart.xAxis
        right.valueFormatter = MyValueFormatter()
        right.position = XAxis.XAxisPosition.BOTTOM
        right.labelCount = usdDataValues().size - 1
        right.setDrawLabels(true); // no axis labels
        right.setDrawAxisLine(true); // no axis line
        right.setDrawGridLines(false); // no grid lines

        val left: YAxis = lineChart.axisLeft
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines

        val lineDataSet = LineDataSet(usdDataValues(), "USD")
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = baseContext.resources.getDrawable(R.drawable.gradient)
        lineDataSet.lineWidth = 1f
        lineDataSet.color = Color.BLUE
        lineDataSet.setCircleColor(Color.BLUE)

        val dataSet: MutableList<ILineDataSet> = mutableListOf()
        dataSet.add(lineDataSet)

        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }

    private fun usdDataValues(): List<Entry> {
        val list: MutableList<Entry> = mutableListOf()
        list.add(Entry(0f, 24.7f))
        list.add(Entry(1f, 23.7f))
        list.add(Entry(2f, 25.5f))
        list.add(Entry(3f, 26.3f))
        list.add(Entry(4f, 27.5f))
        return list
    }

    class MyValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return when (value) {
                0f -> "mon"
                1f -> "tue"
                2f -> "wen"
                3f -> "tho"
                4f -> "fri"
                else -> ""
            }
        }
    }
}