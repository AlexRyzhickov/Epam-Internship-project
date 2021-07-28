package com.epam.bet.modules


import com.epam.bet.MainApp
import com.epam.bet.viewmodel.BetsViewModel
import com.epam.bet.viewmodel.FollowersViewModel
import com.epam.bet.viewmodel.IFollowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MainApp() }

    viewModel{ BetsViewModel( get() )}

    single { FollowersViewModel( get() ) }
    single { IFollowViewModel( get() )}
}