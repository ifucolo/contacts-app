package example.com.phonecontactsapp.feature.home.infrastructure

import example.com.phonecontactsapp.feature.home.domain.ContactsSource
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import example.com.phonecontactsapp.feature.home.infrastructure.mapper.ContactsMapper
import example.com.phonecontactsapp.feature.pref.ContactsPref
import io.reactivex.Observable


class ContactsRepository constructor(private val contactsPref: ContactsPref) : ContactsSource {

    override fun fetchContacts(): Observable<List<ContactDetail>> {

        return Observable.create { emitter ->
            val contacts= contactsPref.getContacts()
            emitter.onNext(ContactsMapper.map(contacts))
            emitter.onComplete()
        }
    }
}

