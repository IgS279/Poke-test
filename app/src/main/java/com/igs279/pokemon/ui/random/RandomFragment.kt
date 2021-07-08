package com.igs279.pokemon.ui.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.igs279.pokemon.R
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        randomViewModel.pokeEntity.observe(viewLifecycleOwner){ pokeEntity ->
            binding.randomTextViewId.text = pokeEntity.id
            binding.randomTextViewName.text = pokeEntity.name
            binding.randomTextViewHeight.text = pokeEntity.height
            binding.randomTextViewWeight.text = pokeEntity.weight
            binding.randomTextViewBaseExperience.text = pokeEntity.experience

            binding.randomTextId.visibility = View.VISIBLE
            binding.randomTextName.visibility = View.VISIBLE
            binding.randomTextHeight.visibility = View.VISIBLE
            binding.randomTextWeight.visibility = View.VISIBLE
            binding.randomTextBaseExperience.visibility = View.VISIBLE
            binding.imageViewRandomFavPoke.visibility = View.VISIBLE
        }

        randomViewModel.hasNetwork.observe(viewLifecycleOwner){ hasNetwork ->
            if (!hasNetwork){
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

    }
}