package example.com.phonecontactsapp.feature.home.ui

import android.Manifest
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import example.com.phonecontactsapp.R
import example.com.phonecontactsapp.feature.detail.ui.ContactDetailActivity
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import example.com.phonecontactsapp.utill.extensions.*
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private var progressDialog: ProgressDialog? = null

    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12366

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
            }
        })

        viewModel.loadData.observe(this, Observer { isLoading ->
            when(isLoading) {
                true -> showLoading()
                false -> hideLoading()
            }
        })
    }

    private fun setupList(contactDetails: List<ContactDetail>) = with(recyclerView) {
        setVisibilityLayout(false, true)

        val linearLayoutManager = LinearLayoutManager(this@HomeActivity)

        layoutManager = linearLayoutManager
        adapter = HomeAdapter(contactDetails) { contact, imageView ->
            openDetail(contact, imageView)
        }
    }

    private fun openDetail(contactDetail: ContactDetail, imageView: ImageView) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                imageView,
                getString(R.string.transition_id))

        startActivity(ContactDetailActivity.launchIntent(this, contactDetail), options.toBundle())
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
        setVisibilityLayout(true, false)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        } else
            viewModel.fetchContacts()
    }

    private fun setVisibilityLayout(isShowTryAgain: Boolean, isShowList: Boolean) {
        layoutTryAgain.visible(isShowTryAgain)
        recyclerView.visible(isShowList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    checkPermission()
                else
                    setVisibilityLayout(true, false)

                return
            }
        }
    }
}
