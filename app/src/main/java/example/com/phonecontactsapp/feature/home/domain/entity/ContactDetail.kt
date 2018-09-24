package example.com.phonecontactsapp.feature.home.domain.entity

import java.io.Serializable

class ContactDetail(
        var name: String,
        var imgUrl: String,
        var phoneNumber: String,
        var email: String,
        var address: String,
        var webSite: String,
        var birthDay: String,
        var company: String

) : Serializable