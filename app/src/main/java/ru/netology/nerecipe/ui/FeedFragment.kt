package ru.netology.nerecipe.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
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
        var categoryIsChecked = false

        if (!AppActivity.hasNotEmptyDb) {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.edit {
                putBoolean("dbIsPresent",true)
                commit()
            }
        }

        val checkList = mutableSetOf(
            binding.unknownCuisine,
            binding.europeanCuisine,
            binding.asiaticCuisine,
            binding.panasiaticCuisine,
            binding.easternCuisine,
            binding.americanCuisine,
            binding.russianCuisine,
            binding.mediterranianCuisine
        )

        val adapter = RecipeAdapter(viewModel)
        binding.recipeRecycler.adapter = adapter
        viewModel.recipeData.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category, checkList)
            })
        }


        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.favorites -> {
                    adapter.submitList(viewModel.recipeData.value?.filter {
                        it.isFavorite
                    })
                    true
                }
                R.id.allRecipes -> {
                    checkList.forEach {
                        it.isChecked = true
                    }
                    adapter.submitList(viewModel.recipeData.value)
                    true
                }
                else -> false
            }
        }


        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.filter -> {
                    binding.drawerLayout.open()
                    true
                } else -> false
            }
        }

        binding.unknownCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.unknownCuisine.isChecked = isChecked
            categoryIsChecked = allCheckBoxUnchecked(checkList)
            if (!categoryIsChecked) {
                binding.unknownCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.unknownCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category, checkList)
            })
        }


        binding.europeanCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.europeanCuisine.isChecked = isChecked
            categoryIsChecked = allCheckBoxUnchecked(checkList)
            if (!categoryIsChecked) {
                binding.europeanCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.europeanCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category, checkList)
            })
        }

        binding.asiaticCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.asiaticCuisine.isChecked = isChecked
            categoryIsChecked = allCheckBoxUnchecked(checkList)
            if (!categoryIsChecked) {
                binding.asiaticCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.asiaticCuisine)
                adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                    filter(recipe.category,checkList)
            })
        }

        binding.panasiaticCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.panasiaticCuisine.isChecked = isChecked
            categoryIsChecked = allCheckBoxUnchecked(checkList)
            if (!categoryIsChecked) {
                binding.panasiaticCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.panasiaticCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category,checkList)
            })
        }

        binding.easternCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.easternCuisine.isChecked = isChecked
            if (!categoryIsChecked) {
                binding.easternCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.easternCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category,checkList)
            })
        }

        binding.americanCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.americanCuisine.isChecked = isChecked
            if (!categoryIsChecked) {
                binding.americanCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.americanCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category,checkList)
            })
        }

        binding.russianCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.russianCuisine.isChecked = isChecked
            if (!categoryIsChecked) {
                binding.russianCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.russianCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category,checkList)
            })
        }

        binding.mediterranianCuisine.setOnCheckedChangeListener { button, isChecked ->
            binding.mediterranianCuisine.isChecked = isChecked
            if (!categoryIsChecked) {
                binding.mediterranianCuisine.isChecked = true
                showSnack(binding)
                return@setOnCheckedChangeListener
            }
            checkList.add(binding.mediterranianCuisine)
            adapter.submitList(viewModel.recipeData.value?.filter { recipe ->
                filter(recipe.category,checkList)
            })
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

    private fun filter(category: String, checkList: Set<CheckBox>): Boolean {
        var recipeIsPresent = false
        checkList.forEach {
            if ((category == it.text.toString()) && it.isChecked)
                recipeIsPresent = true
        }
        return recipeIsPresent
    }

    private fun allCheckBoxUnchecked(checkList: Set<CheckBox>) : Boolean {
        var checkBoxIsCheck = false
        checkList.forEach {
            if (it.isChecked) checkBoxIsCheck = true
        }
        return checkBoxIsCheck
    }

    private fun showSnack(binding: FeedFragmentBinding) {
        Snackbar.make(binding.root,
            "Хотя бы одна категория должна быть выбрана", Snackbar.LENGTH_SHORT
        ).show()
    }

}
