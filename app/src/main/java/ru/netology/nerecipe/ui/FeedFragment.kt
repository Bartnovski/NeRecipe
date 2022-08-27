package ru.netology.nerecipe.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.utils.touch_helper.RecipeItemTouchHelperCallback

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FeedFragmentBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        var recipeIsPresent = false


        binding.drawer.menu.forEach {
            it.isChecked = true
            it.setIcon(R.drawable.ic_baseline_check_box_24)
        }


//        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//
//                R.id.search -> {
//                    // Handle search icon press
//                    true
//                }
//
//                else -> false
//            }
//        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecycler.adapter = adapter
        viewModel.recipeData.observe(viewLifecycleOwner) { recipes ->
            recipes.filter { recipe ->
                binding.drawer.menu.forEach {
                    recipeIsPresent = (recipe.category == it.title.toString()) &&
                            it.isChecked
                }
                recipeIsPresent
            }
            adapter.submitList(recipes)
        }


            binding.drawer.setNavigationItemSelectedListener { menuItem ->

                menuItem.isChecked = !menuItem.isChecked
                if (menuItem.isChecked) menuItem.setIcon(R.drawable.ic_baseline_check_box_24)
                else menuItem.setIcon(R.drawable.ic_baseline_check_box_outline_blank_24)

                true
            }


        viewModel.onContentClickEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_feedFragment_to_recipeFragment)
        }

        binding.addRecipe.setOnClickListener {
            RecipeViewModel.addingRecipeFlag = true
            findNavController().navigate(R.id.action_feedFragment_to_addEditRecipeFragment)
        }

        viewModel.onDeleteRecipeClickedEvent.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        viewModel.onRecipeEditClickedEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_feedFragment_to_addEditRecipeFragment)
        }

        val callback = RecipeItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recipeRecycler)





        return binding.root
    }

}
