package example.com.phonecontactsapp

import android.app.Application
import example.com.phonecontactsapp.di.contactModule
import org.koin.android.ext.android.startKoin

class PhoneContactApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(contactModule))

    }
}