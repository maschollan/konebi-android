package com.chollan.konebi.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TabMenuItem(
    title: String,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        AnimatedVisibility(visible = active, enter = scaleIn(), exit = scaleOut()) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun TabMenu(
    data: Array<String>,
    activeMenu: Int,
    onMenuClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    slot: @Composable () -> Unit = {}
) {
    Row(modifier = modifier) {
        for ((index, item) in data.withIndex()) {
            TabMenuItem(
                title = item,
                active = index == activeMenu,
                onClick = { onMenuClick(index) },
                modifier = Modifier.weight(1f)
            )
        }
        slot()
    }
}