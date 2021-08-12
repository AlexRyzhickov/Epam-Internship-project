package com.epam.bet.modules


import com.epam.bet.MainApp
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.InboxViewModel
import com.epam.bet.viewmodel.SharedPreferencesProvider
import com.epam.bet.viewmodel.SubscribeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MainApp() }

    single{ BetsViewModel( get() )}

    single { SubscribeViewModel( get() )}
    single { InboxViewModel( get() ) }
    single { SharedPreferencesProvider( get() ) }

}