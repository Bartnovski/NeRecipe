package ru.netology.nerecipe.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecycler.adapter = adapter
        viewModel.recipeData.observe(viewLifecycleOwner) { recipe ->
            adapter.submitList(recipe)
        }

        viewModel.onContentClickEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_feedFragment_to_recipeFragment)
        }


        return binding.root
    }
}
