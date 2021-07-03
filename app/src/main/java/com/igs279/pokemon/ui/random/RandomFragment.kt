package com.igs279.pokemon.ui.random

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.igs279.pokemon.R
import com.igs279.pokemon.TAG
import com.igs279.pokemon.databinding.FragmentRandomBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomFragment : Fragment() {

    private val randomViewModel: RandomViewModel by viewModel()
    private lateinit var binding: FragmentRandomBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_random, container, false)
        binding.randomViewModel = randomViewModel
        binding.lifecycleOwner = this

        binding.buttonRandom.setOnClickListener{
            randomViewModel.searchPokeById()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "RandomFragment onViewCreated +")

        randomViewModel.id.observe(viewLifecycleOwner){ id ->
            Log.i(TAG, "randomFragment.id  id= $id")
            binding.randomTextViewId.text = id
            binding.randomTextId.visibility = View.VISIBLE
        }

        randomViewModel.myName.observe(viewLifecycleOwner){ name ->
            binding.randomTextViewName.text = name
            binding.randomTextName.visibility = View.VISIBLE
        }

        randomViewModel.height.observe(viewLifecycleOwner){ height ->
            binding.randomTextViewHeight.text = height
            binding.randomTextHeight.visibility = View.VISIBLE
        }

        randomViewModel.weight.observe(viewLifecycleOwner){ weight ->
            binding.randomTextViewWeight.text = weight
            binding.randomTextWeight.visibility = View.VISIBLE
        }

        randomViewModel.experience.observe(viewLifecycleOwner){ experience ->
            binding.randomTextViewBaseExperience.text = experience
            binding.randomTextBaseExperience.visibility = View.VISIBLE
            binding.imageViewRandomFavPoke.visibility = View.VISIBLE
        }
    }
}