package example.com.phonecontactsapp.feature.pref

import android.content.Context
import com.github.tamir7.contacts.Contacts

class ContactsPref constructor(val context: Context) {

    init {
        Contacts.initialize(context)
    }

    fun getContacts()= Contacts.getQuery().find()
}