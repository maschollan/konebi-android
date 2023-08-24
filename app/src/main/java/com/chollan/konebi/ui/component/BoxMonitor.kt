package com.chollan.konebi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chollan.konebi.data.MonitorData

@Composable
fun BoxMonitor(monitorData: MonitorData, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(112.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = monitorData.title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight(500)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = monitorData.value,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight(500),
                )
                Text(
                    text = monitorData.satuan,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight(500),
                )
            }
        }
    }
}

@Composable
fun BoxMonitorList(data: Array<MonitorData>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        for ((index, item) in data.withIndex()) {
            BoxMonitor(
                item,
                modifier = Modifier.padding(bottom = if (index != (data.size - 1)) 16.dp else 0.dp)
            )
        }
    }
}