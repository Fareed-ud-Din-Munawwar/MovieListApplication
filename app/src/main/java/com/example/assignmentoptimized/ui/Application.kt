package com.example.assignmentoptimized.ui

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.androidnetworking.AndroidNetworking
import com.example.assignmentoptimized.dependencyinjection.BindingComponentBuilder
import com.example.assignmentoptimized.dependencyinjection.DataBindingEntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var bindingComponentProvider: Provider<BindingComponentBuilder>
    override fun onCreate() {
        super.onCreate()

        val dataBindingComponent = bindingComponentProvider.get().build()
        val dataBindingEntryPoint =
            EntryPoints.get(dataBindingComponent, DataBindingEntryPoint::class.java)
        DataBindingUtil.setDefaultComponent(dataBindingEntryPoint)

        // Adding an Network Interceptor for Debugging purpose :
        val okHttpClient = OkHttpClient().newBuilder().build()
        AndroidNetworking.initialize(this, okHttpClient)
    }


}