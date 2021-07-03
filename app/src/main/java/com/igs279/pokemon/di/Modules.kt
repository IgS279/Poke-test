package com.igs279.pokemon.di


import com.igs279.pokemon.data.Repository
import com.igs279.pokemon.data.remote.RemoteDataSource
import com.igs279.pokemon.ui.random.RandomViewModel
import com.igs279.pokemon.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { SearchViewModel(get()) }
}

val randomViewModelModule = module {
    viewModel { RandomViewModel(get()) }
}

val repoModule = module {

    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource.instance
    }

    fun provideRepository(remoteDataSource: RemoteDataSource)
            : Repository {
        return Repository(remoteDataSource)
    }

    single { provideRemoteDataSource() }
    single { provideRepository(get()) }

}

