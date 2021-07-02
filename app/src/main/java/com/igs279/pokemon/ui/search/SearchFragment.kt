package com.igs279.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.buttonSearch.setOnClickListener{
            searchViewModel.searchPokeByName(binding.editTextSearch.text.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "SearchFragment onViewCreated +")

        searchViewModel.id.observe(viewLifecycleOwner){ id ->
            Log.i(TAG, "searchViewModel.id  id= $id")
            binding.textViewId.text = id
            binding.textId.visibility = View.VISIBLE
        }

        searchViewModel.myName.observe(viewLifecycleOwner){ name ->
            binding.textViewName.text = name
            binding.textName.visibility = View.VISIBLE
        }

        searchViewModel.height.observe(viewLifecycleOwner){ height ->
            binding.textViewHeight.text = height
            binding.textHeight.visibility = View.VISIBLE
        }

        searchViewModel.weight.observe(viewLifecycleOwner){ weight ->
            binding.textViewWeight.text = weight
            binding.textWeight.visibility = View.VISIBLE
        }

        searchViewModel.experience.observe(viewLifecycleOwner){ experience ->
            binding.textViewBaseExperience.text = experience
            binding.textBaseExperience.visibility = View.VISIBLE
            binding.imageViewFavPoke.visibility = View.VISIBLE
        }
    }
}