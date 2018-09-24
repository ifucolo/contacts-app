package example.com.phonecontactsapp.feature.home.infrastructure

import android.content.Context
import example.com.phonecontactsapp.feature.home.domain.ContactsSource
import example.com.phonecontactsapp.feature.home.domain.entity.Contact
import io.reactivex.Observable
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email.DISPLAY_NAME
import example.com.phonecontactsapp.feature.home.infrastructure.mapper.ContactsMapper


class ContactsRepository constructor(private val context: Context) : ContactsSource {

    override fun fetchContacts(): Observable<List<Contact>> {
        val phones = context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null)

        return Observable.just(ContactsMapper.map(phones, context.contentResolver))
    }

}

