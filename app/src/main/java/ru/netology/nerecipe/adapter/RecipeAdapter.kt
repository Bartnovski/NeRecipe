package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.databinding.RecipeLayoutBinding
import ru.netology.nerecipe.databinding.RecipiesForFeedBinding
import ru.netology.nerecipe.models.RecipeModel

class RecipeAdapter(
    private val interactionListener: InteractionListener
) : ListAdapter<RecipeModel,RecipeAdapter.RecipeHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipiesForFeedBinding.inflate(inflater, parent, false)
        return RecipeHolder(binding,interactionListener)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    class RecipeHolder(
        private val binding : RecipiesForFeedBinding,
        listener: InteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var recipe: RecipeModel

        init {
            binding.recipeImage.setOnClickListener {
                listener.showDetailedView(recipe)
            }
        }

        fun bind(recipe: RecipeModel) = with(binding) {
            this@RecipeHolder.recipe = recipe

        }
    }


    private object DiffCallback : DiffUtil.ItemCallback<RecipeModel>() {
        override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean =
            oldItem.equals(newItem)
    }

}