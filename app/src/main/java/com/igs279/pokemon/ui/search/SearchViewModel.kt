package com.igs279.pokemon.ui.search

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igs279.pokemon.TAG
import com.igs279.pokemon.data.Repository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private var _id = MutableLiveData<String>()
    val id: LiveData<String>
        get() = _id

    private var _name = MutableLiveData<String>()
    val myName: LiveData<String>
        get() = _name

    private var _height = MutableLiveData<String>()
    val height: LiveData<String>
        get() = _height

    private var _weight = MutableLiveData<String>()
    val weight: LiveData<String>
        get() = _weight

    private var _experience = MutableLiveData<String>()
    val experience: LiveData<String>
        get() = _experience

    private var _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl


    fun searchPokeByName(searchText: String){
        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "searchText = $searchText")
            Log.i(TAG, "getSearchPokeData = ${repository.getSearchPokeData(searchText)}")
            val resp = repository.getSearchPokeData(searchText)
            _id.postValue(resp?.id.toString())
            _name.postValue(resp?.name)
            _height.postValue(resp?.height.toString())
            _weight.postValue(resp?.weight.toString())
            _experience.postValue(resp?.baseExperience.toString())
            _imageUrl.postValue(resp?.sprites?.frontDefault.toString())
        }
    }

}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Log.i(TAG, "loadImage +    imageUrl = $imageUrl")
    Picasso.get()
            .load(imageUrl)
            //.placeholder(R.drawable.ic_launcher_foreground)
            .into(view)
}
