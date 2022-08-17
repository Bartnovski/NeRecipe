package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.RecipeLayoutBinding
import ru.netology.nerecipe.models.RecipeModel

class RecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = RecipeLayoutBinding.inflate(inflater,container,false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeModel.StepAdapter(viewModel)
        binding.stepRecycler.adapter = adapter
        viewModel.stepData.observe(viewLifecycleOwner) { step ->
            adapter.submitList(step)
        }

        return binding.root
    }

}