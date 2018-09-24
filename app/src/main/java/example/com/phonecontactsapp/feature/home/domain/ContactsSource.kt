package example.com.phonecontactsapp.feature.home.domain

import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import io.reactivex.Observable

interface ContactsSource {

    fun fetchContacts(): Observable<List<ContactDetail>>
}