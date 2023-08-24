package com.chollan.konebi.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chollan.konebi.R
import com.chollan.konebi.ui.component.Header
import com.chollan.konebi.ui.component.PowerButton


@Composable
fun ControlScreen(
    navController: NavHostController,
    powerKonveyor: Boolean,
    powerPump: Boolean,
    onKonveyorClick: () -> Unit,
    onPumpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
//    var powerKonveyor by rememberSaveable { mutableStateOf(false) }
//    var powerPump by rememberSaveable { mutableStateOf(false) }
//    var speedKonveyor by rememberSaveable { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PowerButton(
            active = powerKonveyor,
            onClick = onKonveyorClick,
            title = "Power Konveyor"
        )
        Spacer(modifier = Modifier.size(20.dp))
        PowerButton(
            active = powerPump,
            onClick = onPumpClick,
            title = "Power Pompa"
        )

//        Slider(
//            value = speedKonveyor,
//            onValueChange = { speedKonveyor = it },
//            valueRange = 0f..255f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp)
//        )
//
//        Text(
//            text = "Speed Konveyor ${speedKonveyor.toInt()}",
//            fontSize = 24.sp,
//            fontWeight = FontWeight(600),
//            textAlign = TextAlign.Center,
//            color =  MaterialTheme.colorScheme.onSurface,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewControlScreen() {
//    ControlScreen(navController = rememberNavController())
//}