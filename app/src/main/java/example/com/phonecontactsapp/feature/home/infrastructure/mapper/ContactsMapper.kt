package example.com.phonecontactsapp.feature.home.infrastructure.mapper

import com.github.tamir7.contacts.Contact
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail


object ContactsMapper {

    fun map(list: List<Contact>) = list.map { map(it) }

    fun map(contact: Contact) = ContactDetail(
        name = "${contact.displayName.orEmpty()} ${contact.familyName.orEmpty()}",
        imgUrl = contact.photoUri?.toString().orEmpty(),
        phoneNumber = contact.phoneNumbers?.firstOrNull()?.number.orEmpty(),
        email = contact.emails?.firstOrNull()?.address.orEmpty(),
        address = contact.addresses?.firstOrNull()?.formattedAddress.orEmpty(),
        webSite = contact.websites?.firstOrNull().orEmpty(),
        birthDay = contact.birthday?.startDate.orEmpty(),
        company = contact.companyName.orEmpty()
    )

}




