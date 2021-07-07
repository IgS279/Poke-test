package com.igs279.pokemon.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.igs279.pokemon.R
import com.igs279.pokemon.data.models.PokeEntityDb
import com.igs279.pokemon.databinding.FragmentFavBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavFragment : Fragment(), OnCustomClickListener {

    private val favViewModel: FavViewModel by viewModel()
    private lateinit var binding: FragmentFavBinding
    private lateinit var favAdapter: FavAdapter
    private lateinit var favRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_fav, container, false)
        binding.favViewModel = favViewModel
        binding.lifecycleOwner = this

        favRecycler = binding.favRecycler
        favRecycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        favViewModel.getPokeEntity().observe(viewLifecycleOwner) { pokes ->
            val adapter = FavAdapter(pokes, this)
            favRecycler.adapter = adapter
        }

        favRecycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        return binding.root
    }

    override fun onItemClick(view: View, item: PokeEntityDb) {
            favViewModel.deletePoke(item)
    }
}