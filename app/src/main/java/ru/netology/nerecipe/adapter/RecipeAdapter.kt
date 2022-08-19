package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.RecipiesForFeedBinding
import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.utils.touch_helper.RecipeTouchHelperAdapter
import java.util.*

class RecipeAdapter(
    private val interactionListener: InteractionListener
) : ListAdapter<RecipeModel,RecipeAdapter.RecipeHolder>(DiffCallback), RecipeTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipiesForFeedBinding.inflate(inflater, parent, false)
        return RecipeHolder(binding,interactionListener)
    }

    private val viewModel = RecipeViewModel()
    private val recipeList = viewModel.recipeData.value?.toMutableList()


    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        Picasso.get()
            .load(recipe.recipeImagePath)
            .into(holder.binding.recipeImage)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(recipeList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(recipeList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }


    class RecipeHolder(
        val binding : RecipiesForFeedBinding,
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