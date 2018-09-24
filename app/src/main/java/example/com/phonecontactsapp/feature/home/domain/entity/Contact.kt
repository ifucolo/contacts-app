package example.com.phonecontactsapp.feature.home.domain.entity

import java.io.Serializable

class Contact(
        var name: String? = null,
        var imgUrl: String? = null,
        var phoneNumber: String? = null,
        var email: String? = null,
        var address: String? = null

) : Serializable