package example.com.phonecontactsapp.feature.home.infrastructure.mapper

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import example.com.phonecontactsapp.feature.home.domain.entity.Contact


object ContactsMapper {

    private val EMAIL_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI
    private val EMAIL_SELECTION = "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?"
    private val EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA

    private val ADDRESS_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI
    private val ADDRESS_SELECTION = "${ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID} = ?"
    private val ADDRESS_DATA = ContactsContract.CommonDataKinds.StructuredPostal.DATA

    fun map(phones: Cursor?, contentResolver: ContentResolver) = ArrayList<Contact>().apply {

        if (phones?.moveToFirst() == true) {
            do {
                val id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID))

                add(
                        Contact(
                                name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                                imgUrl = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)).orEmpty(),
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).orEmpty(),
                                email = addData(contentResolver, id, EMAIL_URI, EMAIL_SELECTION, EMAIL_DATA),
                                address = addData(contentResolver, id, ADDRESS_URI, ADDRESS_SELECTION, ADDRESS_DATA))
                )

            } while (phones.moveToNext())
        }

        phones?.close()
    }


    private fun addData(contentResolver: ContentResolver, id: String, uri: Uri, selection: String, data: String): String? {
        val dataList = ArrayList<String>()
        val cursor = contentResolver.query(uri, null, selection, arrayOf(id), data)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val email = cursor.getString(cursor.getColumnIndex(data))
                dataList.add(email)
            } while (cursor.moveToNext())
            cursor.close()
        }


        return if (!dataList.isEmpty()) null else dataList.toString()
    }
}


