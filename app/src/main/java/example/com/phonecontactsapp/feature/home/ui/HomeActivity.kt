package example.com.phonecontactsapp.feature.home.ui

import android.Manifest
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import example.com.phonecontactsapp.R
import example.com.phonecontactsapp.feature.home.domain.entity.Contact
import example.com.phonecontactsapp.utill.extensions.progressDialog
import example.com.phonecontactsapp.utill.extensions.toast
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import example.com.phonecontactsapp.feature.detail.ui.ContactDetailActivity
import example.com.phonecontactsapp.utill.extensions.hide
import example.com.phonecontactsapp.utill.extensions.show


class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private var progressDialog: ProgressDialog? = null

    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12366
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binListener()
        setupViewModel()

        checkPermission()
    }

    private fun binListener() {
        btnTryAgain.setOnClickListener {
            checkPermission()
        }
    }

    private fun setupViewModel() {
        viewModel.uiData.observe(this, Observer { uiData ->
            when {
                uiData?.list?.isNotEmpty() == true -> setupList(uiData.list)
                uiData?.error != null -> showError(uiData.error)
                else -> showEmptyState()
            }
        })

        viewModel.loadData.observe(this, Observer { isLoading ->
            when(isLoading) {
                true -> showLoading()
                false -> hideLoading()
            }
        })
    }

    private fun setupList(contacts: List<Contact>) = with(recyclerView) {
        show()
        layoutTryAgain.hide()

        val gridLayoutManager = GridLayoutManager(this@HomeActivity, 2)

        layoutManager = gridLayoutManager
        adapter = HomeAdapter(contacts) { contact, imageView ->
            openDetail(contact, imageView)
        }
    }

    private fun openDetail(contact: Contact, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                imageView,
                getString(R.string.transition_id))

        startActivity(ContactDetailActivity.launchIntent(this, contact), options.toBundle())
    }

    private fun hideLoading() {
        if (progressDialog?.isShowing == true)
            progressDialog!!.dismiss()
    }

    private fun showLoading() {
        progressDialog = progressDialog(R.string.loading)
    }

    private fun showError(error: Throwable?) {
        toast(R.string.error)
    }

    private fun showEmptyState() {

    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            viewModel.fetchContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkPermission()
                } else {
                    layoutTryAgain.show()
                    recyclerView.hide()
                }
                return
            }
        }
    }
}
