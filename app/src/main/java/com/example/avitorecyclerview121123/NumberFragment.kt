package com.example.avitorecyclerview121123

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avitorecyclerview121123.databinding.FragmentNumberBinding
import kotlinx.coroutines.flow.collectLatest

class NumberFragment : Fragment() {

    private lateinit var viewModel: NumberViewModel
    private lateinit var binding: FragmentNumberBinding

    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NumberViewModel::class.java]

        recyclerViewAdapter = RecyclerViewAdapter().apply {
            attachDelegate(object : NumberDelegate {
                override fun removeElement(number: Int) {
                    viewModel.removeElement(number)
                }
            })
        }

        binding.numberRecycler.adapter = recyclerViewAdapter

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> binding.numberRecycler.layoutManager =
                GridLayoutManager(context, 2)
            else -> binding.numberRecycler.layoutManager = GridLayoutManager(context, 4)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.numberList.collectLatest {
                recyclerViewAdapter?.submitList(it)
            }
        }


    }

}