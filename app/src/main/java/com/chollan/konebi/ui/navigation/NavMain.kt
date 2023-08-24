package com.chollan.konebi.ui.navigation

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chollan.konebi.R
import com.chollan.konebi.api.ApiConfig
import com.chollan.konebi.mqtt.publish
import com.chollan.konebi.mqtt.subscribe
import com.chollan.konebi.response.LaporanItem
import com.chollan.konebi.ui.component.BottomBar
import com.chollan.konebi.ui.component.Header
import com.chollan.konebi.ui.screen.ControlScreen
import com.chollan.konebi.ui.screen.HomeScreen
import com.chollan.konebi.ui.screen.ReportScreen
import com.chollan.konebi.ui.screen.SplashScreen
import com.chollan.konebi.viewmodel.ApiViewModel
import com.chollan.konebi.viewmodel.ApiViewModelFactory
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavMain(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val apiViewModel: ApiViewModel = viewModel(factory = ApiViewModelFactory())
        val context = LocalContext.current

        var berat1 by rememberSaveable { mutableStateOf("0") }
        var berat2 by rememberSaveable { mutableStateOf("0") }
        var berat3 by rememberSaveable { mutableStateOf("0") }

        var jumlah1 by rememberSaveable { mutableStateOf("0") }
        var jumlah2 by rememberSaveable { mutableStateOf("0") }
        var jumlah3 by rememberSaveable { mutableStateOf("0") }

        var konveyor by rememberSaveable { mutableStateOf(false) }
        var pompa by rememberSaveable { mutableStateOf(false) }

        val serverUri = "tcp://13.228.44.216:1883"
        val topicKamera = "topic/kamera"
        val topicBerat = "topic/berat"
        val topicJumlah = "topic/jumlah"
        val topicGetBerat = "topic/get/berat"
        val topicGetJumlah = "topic/get/jumlah"
        val topicSaveddata = "topic/saveddata"
        val topicKonveyor = "topic/konveyor"
        val topicPompa = "topic/pompa"
        val topicGetKonveyor = "topic/get/konveyor"
        val topicGetPompa = "topic/get/pompa"

        val mqttClient =
            MqttAndroidClient(context, serverUri, MqttClient.generateClientId())

        val mqttClientImage =
            MqttAndroidClient(context, serverUri, MqttClient.generateClientId())

        val base64Image by apiViewModel.base64Image

        LaunchedEffect(true) {
            mqttClient.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    Log.d("MQTT TEST", "Connection lost ${cause.toString()}")
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d("MQTT TEST", "Message $topic arrived: ${message.toString()}")
                    if (topic == topicBerat) {
                        val berat = message.toString().split("|")
                        berat1 = berat[0]
                        berat2 = berat[1]
                        berat3 = berat[2]
                    }
                    if (topic == topicJumlah) {
                        val jumlah = message.toString().split("|")
                        jumlah1 = jumlah[0]
                        jumlah2 = jumlah[1]
                        jumlah3 = jumlah[2]
                    }
                    if (topic == topicSaveddata) {
                        berat1 = "0"
                        berat2 = "0"
                        berat3 = "0"
                        jumlah1 = "0"
                        jumlah2 = "0"
                        jumlah3 = "0"
                        apiViewModel.getLaporan()
                        Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    }
                    if (topic == topicKonveyor) {
                        konveyor = if (message.toString() == "on") {
                            true
                        } else if (message.toString() == "off") {
                            false
                        } else {
                            konveyor
                        }
                    }
                    if (topic == topicPompa) {
                        pompa = if (message.toString() == "on") {
                            true
                        } else if (message.toString() == "off") {
                            false
                        } else {
                            pompa
                        }
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d("MQTT TEST", "Delivery complete")
                }
            })

            val options = MqttConnectOptions()

            try {
                mqttClient.connect(options, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d("MQTT Konebi", "connection success")
                        subscribe(mqttClient, topicBerat)
                        subscribe(mqttClient, topicJumlah)
                        subscribe(mqttClient, topicSaveddata)
                        subscribe(mqttClient, topicKonveyor)
                        subscribe(mqttClient, topicPompa)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d("MQTT Konebi", "connection failure")
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }

            mqttClientImage.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    Log.d("MQTT IMAGE", "Connection lost ${cause.toString()}")
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    if (topic == topicKamera) {
                        apiViewModel.setBase64Image(message.toString())
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d("MQTT IMAGE", "Delivery complete")
                }
            })

            try {
                mqttClientImage.connect(options, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d("MQTT IMAGE", "connection success")
                        subscribe(mqttClientImage, topicKamera)
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d("MQTT IMAGE", "connection failure")
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }

            delay(2000)

            publish(mqttClient, topicGetBerat, "")
            publish(mqttClient, topicGetJumlah, "")
            publish(mqttClient, topicGetKonveyor, "")
            publish(mqttClient, topicGetPompa, "")
        }



        AnimatedVisibility(
            visible = currentRoute != Screen.Splash.route && currentRoute != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Header(title = currentRoute ?: "", modifier = Modifier.fillMaxWidth())
        }

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.weight(1f)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.Home.route) {
                val view = LocalView.current
                val colorScheme = MaterialTheme.colorScheme
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    val window = (view.context as Activity).window
                    window.navigationBarColor = colorScheme.primary.toArgb()
                    WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                        darkTheme
                }
                HomeScreen(
                    navController,
                    listOf(berat1, berat2, berat3),
                    listOf(jumlah1, jumlah2, jumlah3),
                    base64Image
                )
            }

            composable(Screen.Report.route) {
                ReportScreen(navController, apiViewModel)
            }

            composable(Screen.Control.route) {
                ControlScreen(navController, konveyor, pompa,
                    onKonveyorClick = {
                        try {
                            mqttClient.connect(
                                MqttConnectOptions(),
                                null,
                                object : IMqttActionListener {
                                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                                        Log.d("MQTT Konebi", "connection success")
                                        publish(
                                            mqttClient,
                                            topicKonveyor,
                                            if (konveyor) "off" else "on"
                                        )
                                    }

                                    override fun onFailure(
                                        asyncActionToken: IMqttToken?,
                                        exception: Throwable?
                                    ) {
                                        Log.d("MQTT Konebi", "connection failure")
                                    }
                                })
                        } catch (e: MqttException) {
                            e.printStackTrace()
                        }
                    },
                    onPumpClick = {
                        try {
                            mqttClient.connect(
                                MqttConnectOptions(),
                                null,
                                object : IMqttActionListener {
                                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                                        Log.d("MQTT Konebi", "connection success")
                                        publish(
                                            mqttClient,
                                            topicPompa,
                                            if (pompa) "off" else "on"
                                        )
                                    }

                                    override fun onFailure(
                                        asyncActionToken: IMqttToken?,
                                        exception: Throwable?
                                    ) {
                                        Log.d("MQTT Konebi", "connection failure")
                                    }
                                })
                        } catch (e: MqttException) {
                            e.printStackTrace()
                        }
                    })
            }
        }

        AnimatedVisibility(
            visible = currentRoute != Screen.Splash.route && currentRoute != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            BottomBar(navController, onSaveClick = {
                val laporanItem = LaporanItem(
                    berat = "$berat1|$berat2|$berat3",
                    jumlah = "$jumlah1|$jumlah2|$jumlah3",
                    createdAt = LocalDateTime.now().toString(),
                )
                apiViewModel.postLaporan(laporanItem)
                try {
                    mqttClient.connect(MqttConnectOptions(), null, object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            Log.d("MQTT Konebi", "connection success")
                            publish(mqttClient, topicSaveddata, "")
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable?
                        ) {
                            Log.d("MQTT Konebi", "connection failure")
                        }
                    })
                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            })
        }
    }
}