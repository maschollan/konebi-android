package com.chollan.konebi.mqtt

import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

fun publish(mqttClient: MqttAndroidClient, topic: String, msg: String) {
    val message = MqttMessage(msg.toByteArray())
    message.qos = 1
    message.isRetained = false
    try {
        mqttClient.publish(topic, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MQTT Konebi", "publish $topic success")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d("MQTT Konebi", "publish $topic failure")
            }
        })
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun subscribe(mqttClient: MqttAndroidClient, topic: String) {
    try {
        mqttClient.subscribe(
            topic,
            1,
            null,
            object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("MQTT Konebi", "subscribe $topic success")
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken?,
                    exception: Throwable?
                ) {
                    Log.d("MQTT Konebi", "subscribe $topic failure")
                }
            })

    } catch (e: MqttException) {
        e.printStackTrace()
    }
}