package example.com.phonecontactsapp.feature.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import example.com.phonecontactsapp.R
import example.com.phonecontactsapp.feature.home.domain.entity.Contact
import example.com.phonecontactsapp.utill.extensions.setTextOrHide
import kotlinx.android.synthetic.main.activity_contact_detail.*
import java.lang.Exception


class ContactDetailActivity : AppCompatActivity() {

    private val contact by lazy { intent.getSerializableExtra(EXTRA_CONTACT) as Contact}

    companion object {
        const val EXTRA_CONTACT = "contact"

        fun launchIntent(context: Context, contact: Contact): Intent {
            val intent = Intent(context, ContactDetailActivity::class.java)
            intent.putExtra(EXTRA_CONTACT, contact)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        setHeader()
        setTransaction()
        setInfoData()
    }


    private fun setHeader() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setTransaction() {
        supportPostponeEnterTransition()

        Glide.with(this)
                .load(contact.imgUrl)
                .dontAnimate()
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception?, model: String?, target: com.bumptech.glide.request.target.Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: com.bumptech.glide.request.target.Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(imgContact)
    }

    private fun setInfoData() {
        toolbar.title = contact.name
        collapsingToolbarLayout.title = contact.name

        txtPhoneNumber.setTextOrHide(nestedScrollView, getString(R.string.phone_number), contact.phoneNumber.orEmpty())
        txtEmail.setTextOrHide(nestedScrollView, getString(R.string.email), contact.email.orEmpty())
        txtAddress.setTextOrHide(nestedScrollView, getString(R.string.address), contact.address.orEmpty())
    }
}
