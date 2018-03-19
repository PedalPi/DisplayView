package io.github.pedalpi.displayview

import android.app.Application
import io.github.pedalpi.displayview.communication.adb.AdbCommunication
import io.github.pedalpi.displayview.communication.base.Communicator


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Communicator.communication = AdbCommunication()
        Communicator.initialize()
    }
}