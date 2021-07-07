package com.igs279.pokemon.ui.search

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.igs279.pokemon.App
import com.igs279.pokemon.TAG
import com.igs279.pokemon.data.Repository
import com.igs279.pokemon.data.models.PokeEntityDb
import com.igs279.pokemon.domain.entities.PokeEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application,
                      private val repository: Repository)
    : AndroidViewModel(application) {

    private var _hasNetwork = MutableLiveData<Boolean>()
    val hasNetwork: LiveData<Boolean>
        get() = _hasNetwork

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl

    private var _pokeEntity = MutableLiveData<PokeEntity>()
    val pokeEntity: LiveData<PokeEntity>
        get() = _pokeEntity


    val favVisible = ObservableBoolean(false)
    val favSelect = ObservableBoolean(false)
    val etText = ObservableField("")


    private fun searchPokeByName(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "searchText = $searchText")
            Log.i(TAG, "getSearchPokeData = ${repository.getSearchPokeData(searchText)}")
            val resp = repository.getSearchPokeData(searchText)
            _imageUrl.postValue(resp?.imageUrl)
            _pokeEntity.postValue(resp!!)
            favSelect.set(false)
        }
    }

    fun onClickSearchButton(){
        if (checkNetwork()) {
            etText.get()?.let { if (it != "") searchPokeByName(it) else searchPokeByName("0") }
            favVisible.set(false)
            _hasNetwork.value = true
            Log.i(TAG, "favVisible.set(false) +")
        } else _hasNetwork.value = false
    }

    fun onClickFavStars(){
        if (!favSelect.get()) {
            viewModelScope.launch(Dispatchers.IO) {
                updateOrInsertPoke()
                Log.i(TAG, "imageViewFavPoke upd/ins check = ${favSelect.get()}")
            }
        } else{
            viewModelScope.launch(Dispatchers.IO) {
                deletePoke()
                Log.i(TAG, "imageViewFavPoke del check = ${favSelect.get()}")
            }
        }
    }

    private suspend fun isFavorite(name: String?): Int {
        return repository.isFavorite(name)
    }

    private suspend fun updateOrInsertPoke() {
        val pokeEntityDb = pokeEntity.value?.let {
            PokeEntityDb(
                    idKey = it.id.toIntOrNull() ?: 0,
                    pokeEntity = it
            )
        }
        if (pokeEntityDb?.idKey != 0) {
            if (isFavorite(pokeEntityDb?.pokeEntity?.name) == 1) {
                pokeEntityDb?.let { repository.updatePoke(it) }
                Log.i(TAG, "pokeEntityDb = $pokeEntityDb")
            } else {
                pokeEntityDb?.let { repository.insertPoke(it) }
                Log.i(TAG, "pokeEntityDb = $pokeEntityDb")
            }
            favSelect.set(true)
        }
    }

    private suspend fun deletePoke() {
        if (isFavorite(pokeEntity.value?.name) == 1) {
            if (pokeEntity.value?.id?.equals("") == false) {
                pokeEntity.value?.id?.toInt()?.let {repository.deleteById(it)
                }
            }
            favSelect.set(false)
            Log.i(TAG, "deletePoke")
        }
    }

    @Suppress("DEPRECATION")
    private fun checkNetwork(): Boolean {
        val cm = getApplication<App>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = cm.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting == true
    }

}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Log.i(TAG, "loadImage +    imageUrl = $imageUrl")
    if (imageUrl?.trim()?.length != 0) {
        Picasso.get()
                .load(imageUrl)
                .into(view)
    }
}
