package example.com.phonecontactsapp.di

import example.com.phonecontactsapp.feature.home.domain.ContactsSource
import example.com.phonecontactsapp.feature.home.infrastructure.ContactsRepository
import example.com.phonecontactsapp.feature.home.ui.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val contactModule = module {

    single<ContactsSource> { ContactsRepository(androidContext()) }

    viewModel { HomeViewModel(get()) }
}
