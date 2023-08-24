package com.chollan.konebi.ui.screen

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chollan.konebi.R
import com.chollan.konebi.data.MonitorData
import com.chollan.konebi.mqtt.publish
import com.chollan.konebi.mqtt.subscribe
import com.chollan.konebi.ui.component.BoxMonitor
import com.chollan.konebi.ui.component.BoxMonitorList
import com.chollan.konebi.ui.component.Header
import com.chollan.konebi.ui.component.TabMenu
import com.chollan.konebi.ui.component.VideoWebView
import com.chollan.konebi.utils.beratTransform
import com.chollan.konebi.utils.decodeBase64ToBitmap
import kotlinx.coroutines.delay
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

@Composable
fun HomeScreen(navController: NavHostController, berats: List<String>, jumlahs: List<String>, base64Image: String, modifier: Modifier = Modifier) {
    val tabMenu = arrayOf("Berat", "Jumlah", "Kamera")
    var selectedTab by rememberSaveable { mutableStateOf(0) }



    val monitorBerat = arrayOf(
        MonitorData("Berat Jenis Udang 1", berats[0].beratTransform()[0], berats[0].beratTransform()[1]),
        MonitorData("Berat Jenis Udang 2", berats[1].beratTransform()[0], berats[1].beratTransform()[1]),
        MonitorData("Berat Jenis Udang 3", berats[2].beratTransform()[0], berats[2].beratTransform()[1]),
    )
    val monitorJumlah = arrayOf(
        MonitorData("Jumlah Jenis Udang 1", jumlahs[0], "ekor"),
        MonitorData("Jumlah Jenis Udang 2", jumlahs[1], "ekor"),
        MonitorData("Jumlah Jenis Udang 3", jumlahs[2], "ekor"),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TabMenu(
            data = tabMenu, activeMenu = selectedTab, onMenuClick = {
                selectedTab = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        AnimatedVisibility(visible = selectedTab == 0, enter = fadeIn(), exit = fadeOut()) {
            BoxMonitorList(
                data = monitorBerat, modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

        AnimatedVisibility(visible = selectedTab == 1, enter = fadeIn(), exit = fadeOut()) {
            BoxMonitorList(
                data = monitorJumlah, modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }

        AnimatedVisibility(visible = selectedTab == 2, enter = fadeIn(), exit = fadeOut()) {
//            VideoWebView(
//                videoUri = "http://172.16.53.183/test/",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            )

            if (base64Image.isNotEmpty()) Image(
                bitmap = decodeBase64ToBitmap(base64Image).asImageBitmap(),
                contentDescription = "Preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen(navController = rememberNavController())
//}
