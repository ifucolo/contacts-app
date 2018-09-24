package example.com.phonecontactsapp.feature.home.ui

import android.arch.lifecycle.MutableLiveData
import example.com.phonecontactsapp.feature.home.domain.ContactsSource
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import example.com.phonecontactsapp.utill.ReactiveViewModel
import example.com.phonecontactsapp.utill.RxUtils
import io.reactivex.rxkotlin.plusAssign

class HomeViewModel constructor(val source: ContactsSource) : ReactiveViewModel() {

    val uiData = MutableLiveData<ResultUIModel>()
    val loadData = MutableLiveData<Boolean>()

    fun fetchContacts() {
        disposables += source.fetchContacts()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe { loadData.value = true }
                .doFinally { loadData.value = false }
                .subscribe(
                        { contacts ->
                            uiData.value = ResultUIModel(contacts)
                        },
                        {
                            uiData.value = ResultUIModel(error = it)
                        }
                )
    }

    data class ResultUIModel(val list: List<ContactDetail> = emptyList(), val error: Throwable? = null)

}