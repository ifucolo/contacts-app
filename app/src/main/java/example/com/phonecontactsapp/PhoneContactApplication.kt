package example.com.phonecontactsapp

import android.app.Application
import com.github.tamir7.contacts.Contacts
import example.com.phonecontactsapp.di.contactModule
import org.koin.android.ext.android.startKoin

class PhoneContactApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Contacts.initialize(this);

        startKoin(this, listOf(contactModule))

    }
}