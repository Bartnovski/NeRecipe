package ru.netology.nerecipe.models

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.adapter.InteractionListener
import ru.netology.nerecipe.databinding.StepLayoutBinding
import ru.netology.nerecipe.utils.touch_helper.RecipeTouchHelperAdapter
import java.util.*

class RecipeModel(
    val id: Long,
    val recipeName: String,
    val author: String,
    val recipeGroup: String,
    val recipeImagePath: Uri?,
    val isFavorite: Boolean = false
) {

    class StepAdapter(
        private val interactionListener: InteractionListener
        ) : ListAdapter<Step, StepAdapter.StepHolder>(DiffCallback), RecipeTouchHelperAdapter {

        val viewModel = RecipeViewModel()
        val stepList: List<Step>? = viewModel.stepData.value

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = StepLayoutBinding.inflate(inflater, parent, false)
            return StepHolder(binding/*interactionListener*/)
        }

        override fun onBindViewHolder(holder: StepHolder, position: Int) {
            val step = getItem(position)
            holder.bind(step)
        }

        class StepHolder(
            private val binding: StepLayoutBinding
            // listener: InteractionListener
        ) : RecyclerView.ViewHolder(binding.root) {

            private lateinit var step: Step
//            private val popupMenu by lazy {
//                PopupMenu(itemView.context,binding.options).apply {
//                    inflate(R.menu.options)
//                    setOnMenuItemClickListener { menuItem ->
//                        when(menuItem.itemId) {
//                            R.id.remove -> {
//                                listener.onDeleteClicked(post)
//                                true
//                            }
//                            R.id.edit -> {
//                                listener.onEditClicked(post)
//                                true
//                            }
//                            else -> false
//                        }
//                    }
//                }

            fun bind(step: Step) = with(binding) {
                this@StepHolder.step = step

                stepContent.text = step.stepContent
                stepImage.setImageURI(step.stepImagePath)
            }
        }


        private object DiffCallback : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
                oldItem == newItem
        }

        override fun onItemMove(fromPosition: Int, toPosition: Int) {
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(stepList, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(stepList, i, i - 1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemDismiss(position: Int) {
            TODO("Not yet implemented")
        }
    }
}

