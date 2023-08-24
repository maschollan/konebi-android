package com.chollan.konebi.ui.screen

import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chollan.konebi.ui.component.BoxMonitorList
import com.chollan.konebi.ui.component.Header
import com.chollan.konebi.ui.component.TabMenu
import com.chollan.konebi.ui.theme.rememberChartStyle
import com.chollan.konebi.ui.theme.rememberMarker
import com.chollan.konebi.utils.toBerat
import com.chollan.konebi.utils.toJumlah
import com.chollan.konebi.utils.toLabels
import com.chollan.konebi.utils.toSimpleDate
import com.chollan.konebi.utils.transformData
import com.chollan.konebi.viewmodel.ApiViewModel
import com.chollan.konebi.viewmodel.ApiViewModelFactory
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.edges.rememberFadingEdges
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.Axis
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportScreen(navController: NavHostController, apiViewModel: ApiViewModel, modifier: Modifier = Modifier) {
    val tabMenu = arrayOf("Berat", "Jumlah")
    var selectedTab by rememberSaveable { mutableStateOf(0) }


    LaunchedEffect(true) {
        apiViewModel.getLaporan()
    }

    val laporan by apiViewModel.laporan
    val settingData by apiViewModel.settingData

    val berat = laporan.transformData().toBerat()
    val jumlah = laporan.transformData().toJumlah()
    val createdAt = laporan.transformData().toLabels()


    val chartEntryModelProducer1 =
        if (berat.size == 3 && jumlah.size == 3)
            if (selectedTab == 0) ChartEntryModelProducer(entriesOf(*berat[0].toTypedArray()))
            else ChartEntryModelProducer(entriesOf(*jumlah[0].toTypedArray()))
        else ChartEntryModelProducer(entriesOf(0))

    val chartEntryModelProducer2 =
        if (berat.size == 3 && jumlah.size == 3)
            if (selectedTab == 0) ChartEntryModelProducer(entriesOf(*berat[1].toTypedArray()))
            else ChartEntryModelProducer(entriesOf(*jumlah[1].toTypedArray()))
        else ChartEntryModelProducer(entriesOf(0))

    val chartEntryModelProducer3 =
        if (berat.size == 3 && jumlah.size == 3)
            if (selectedTab == 0) ChartEntryModelProducer(entriesOf(*berat[2].toTypedArray()))
            else ChartEntryModelProducer(entriesOf(*jumlah[2].toTypedArray()))
        else ChartEntryModelProducer(entriesOf(0))

    val composedChartEntryModelProducer =
        chartEntryModelProducer1 + chartEntryModelProducer2 + chartEntryModelProducer3

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabMenu(
            data = tabMenu, activeMenu = selectedTab, onMenuClick = {
                selectedTab = it
            }, modifier = Modifier
                .fillMaxWidth()
        )

        ProvideChartStyle(rememberChartStyle(chartColors = chartColors)) {

            Chart(
                chart = columnChart(),
                chartModelProducer = composedChartEntryModelProducer,
                startAxis = startAxis(),
                bottomAxis = bottomAxis(valueFormatter = { index, _ ->
                    if (createdAt.isNotEmpty()) createdAt[index.toInt()].toSimpleDate() else ""
                }),
                marker = rememberMarker(),
                fadingEdges = rememberFadingEdges(),
                legend = verticalLegend(
                    items = chartColors.mapIndexed { index, chartColor ->
                        verticalLegendItem(
                            icon = shapeComponent(Shapes.pillShape, chartColor),
                            label = textComponent(
                                color = currentChartStyle.axis.axisLabelColor,
                                textSize = 12.sp,
                                typeface = Typeface.MONOSPACE,
                            ),
                            labelText = if (selectedTab == 0) "Berat Jenis Udang ${index + 1}" else "Jumlah Jenis Udang ${index + 1}",
                        )
                    },
                    iconSize = 8.dp,
                    iconPadding = 10.dp,
                    spacing = 4.dp,
                    padding = dimensionsOf(top = 8.dp),
                ),
                chartScrollState = rememberChartScrollState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp),
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewReportScreen() {
//    ReportScreen(navController = rememberNavController())
//}

private const val COLOR_1_CODE = 0xff916cda
private const val COLOR_2_CODE = 0xffd877d8
private const val COLOR_3_CODE = 0xfff094bb


private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val chartColors = listOf(color1, color2, color3)