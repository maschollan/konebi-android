package com.chollan.konebi.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chollan.konebi.R

@Composable
fun PowerButton(active: Boolean, onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
    IconButton(
        onClick = {
            onClick()
        },
        modifier = modifier
            .size(130.dp)
            .clip(shape = RoundedCornerShape(50))
            .background(color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary)
            .border(
                width = 2.dp,
                color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(50)
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.round_power_settings_new_24),
            contentDescription = "power",
            tint = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(10.dp)
                .size(90.dp)
        )
    }
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight(600),
        textAlign = TextAlign.Center,
        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 20.dp)
    )
    Text(
        text = if (active) "ON" else "OFF",
        fontSize = 36.sp,
        fontWeight = FontWeight(600),
        textAlign = TextAlign.Center,
        color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
    )
}