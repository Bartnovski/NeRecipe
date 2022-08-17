package ru.netology.nerecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nerecipe.databinding.FeedFragmentBinding
import ru.netology.nerecipe.databinding.RecipeLayoutBinding
import ru.netology.nerecipe.databinding.StepLayoutBinding
import ru.netology.nerecipe.models.RecipeModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feedBinding = FeedFragmentBinding.inflate(layoutInflater)
//        val recipeBinding = RecipeLayoutBinding.inflate(layoutInflater)
//        setContentView(recipeBinding.root)
        setContentView(feedBinding.root)


        val viewModel: RecipeViewModel by viewModels()
//        val stepAdapter = RecipeModel.StepAdapter(viewModel)
//        recipeBinding.stepRecycler.adapter = stepAdapter
//        viewModel.stepData.observe(this) { steps ->
//            stepAdapter.submitList(steps)
//        }
        //val viewModel: RecipeViewModel by viewModels()
        val adapter = RecipeAdapter(viewModel)
        feedBinding.recipeRecycler.adapter = adapter
        viewModel.recipeData.observe(this) { recipe ->
            adapter.submitList(recipe)
        }

    }
}