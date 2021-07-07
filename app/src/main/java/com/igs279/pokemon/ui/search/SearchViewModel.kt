package com.igs279.pokemon.ui.search

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igs279.pokemon.TAG
import com.igs279.pokemon.data.Repository
import com.igs279.pokemon.data.models.PokeEntityDb
import com.igs279.pokemon.domain.entities.PokeEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

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
        etText.get()?.let { if (it != "") searchPokeByName(it) else  searchPokeByName("0")}
        favVisible.set(false)
        Log.i(TAG, "favVisible.set(false) +")
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
