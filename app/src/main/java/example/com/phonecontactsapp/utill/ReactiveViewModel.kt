package example.com.phonecontactsapp.utill

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class ReactiveViewModel: ViewModel() {
    val disposables = CompositeDisposable()

    fun disposables() = disposables

    fun unbind() {
        this.disposables.clear()
    }
}
