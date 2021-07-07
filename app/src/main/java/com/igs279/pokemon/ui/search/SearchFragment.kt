package com.igs279.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.igs279.pokemon.R
import com.igs279.pokemon.TAG
import com.igs279.pokemon.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search, container, false)
        binding.searchViewModel = searchViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "SearchFragment onViewCreated +")

        searchViewModel.pokeEntity.observe(viewLifecycleOwner){ pokeEntity ->
            binding.textViewId.text = pokeEntity.id
            binding.textViewName.text = pokeEntity.name
            binding.textViewHeight.text = pokeEntity.height
            binding.textViewWeight.text = pokeEntity.weight
            binding.textViewBaseExperience.text = pokeEntity.experience

            binding.textId.visibility = View.VISIBLE
            binding.textName.visibility = View.VISIBLE
            binding.textHeight.visibility = View.VISIBLE
            binding.textWeight.visibility = View.VISIBLE
            binding.textBaseExperience.visibility = View.VISIBLE
            binding.imageViewFavPoke.visibility = View.VISIBLE
        }

        searchViewModel.hasNetwork.observe(viewLifecycleOwner){ hasNetwork ->
            if (!hasNetwork){
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

}