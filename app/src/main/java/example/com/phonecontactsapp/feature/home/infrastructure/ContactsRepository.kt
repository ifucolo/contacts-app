package example.com.phonecontactsapp.feature.home.infrastructure

import com.github.tamir7.contacts.Contacts
import example.com.phonecontactsapp.feature.home.domain.ContactsSource
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import example.com.phonecontactsapp.feature.home.infrastructure.mapper.ContactsMapper
import io.reactivex.Observable

class ContactsRepository : ContactsSource {

    override fun fetchContacts(): Observable<List<ContactDetail>> {
        return Observable.create { emitter ->
            val contacts = Contacts.getQuery().find()
            emitter.onNext(ContactsMapper.map(contacts))
            emitter.onComplete()
        }
    }
}

