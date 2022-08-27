package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.R
import ru.netology.nerecipe.RecipeViewModel
import ru.netology.nerecipe.databinding.StepLayoutBinding
import ru.netology.nerecipe.models.Step
import ru.netology.nerecipe.utils.touch_helper.RecipeTouchHelperAdapter

class StepAdapter(
    private val interactionListener: InteractionListener
) : ListAdapter<Step, StepAdapter.StepHolder>(StepDiffCallback), RecipeTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepLayoutBinding.inflate(inflater, parent, false)
        return StepHolder(binding,interactionListener)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val step = getItem(position)

        Picasso.get()
            .load(step.stepImagePath)
            .into(holder.binding.stepImage)
        holder.bind(step)
    }

    class StepHolder(
        val binding: StepLayoutBinding,
        listener: InteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var step: Step
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.stepOptions).apply {
                inflate(R.menu.options)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete -> {
                            listener.onStepDeleteClicked(step)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(step)
                            RecipeViewModel.editStepFlag = true
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(step: Step) {
            if (step.stepImagePath.isNullOrBlank()) binding.cardImage.visibility = ImageView.GONE
            else binding.cardImage.visibility = ImageView.VISIBLE
            with(binding) {
                this@StepHolder.step = step
                stepNumber.text = "Шаг " + step.positionInRecipe.toString()
                stepContent.text = step.stepContent
                stepOptions.setOnClickListener { popupMenu.show() }
            }
        }

    }

    private object StepDiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem.stepId == newItem.stepId

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
            oldItem == newItem
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        interactionListener.moveSteps(this.getItem(fromPosition),this.getItem(toPosition))
        notifyItemMoved(fromPosition, toPosition)
    }
}