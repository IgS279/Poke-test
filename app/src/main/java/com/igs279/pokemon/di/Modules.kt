package com.igs279.pokemon.di


import android.app.Application
import com.igs279.pokemon.data.Repository
import com.igs279.pokemon.data.local.AppDatabase
import com.igs279.pokemon.data.local.LocalDataSource
import com.igs279.pokemon.data.local.PokeDAO
import com.igs279.pokemon.data.remote.RemoteDataSource
import com.igs279.pokemon.ui.fav.FavViewModel
import com.igs279.pokemon.ui.random.RandomViewModel
import com.igs279.pokemon.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
}

val randomViewModelModule = module {
    viewModel { RandomViewModel(get(), get()) }
}

val favViewModelModule = module {
    viewModel { FavViewModel(get()) }
}


val repoModule = module {

    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource.instance
    }

    fun provideLocalDataSource(pokeDAO: PokeDAO): LocalDataSource {
        return LocalDataSource(pokeDAO)
    }

    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: LocalDataSource
    )
            : Repository {
        return Repository(remoteDataSource, localDataSource)
    }

    single { provideRemoteDataSource() }
    single { provideLocalDataSource(get()) }
    single { provideRepository(get(), get()) }

}

val databaseModule = module {
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    fun providePokeDao(db: AppDatabase): PokeDAO {
        return db.myPokeDAO()
    }

    single { provideAppDatabase(get()) }
    single { providePokeDao(get()) }
}

