package ru.netology.nerecipe.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
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
import java.util.*

class StepAdapter(
    private val interactionListener: InteractionListener
) : ListAdapter<Step, StepAdapter.StepHolder>(StepDiffCallback), RecipeTouchHelperAdapter {

    private val viewModel = RecipeViewModel(Application())
    private val stepList = viewModel.repository.stepData.value?.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepLayoutBinding.inflate(inflater, parent, false)
        return StepHolder(binding,interactionListener)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val step = getItem(position)
        holder.bind(step)
        Picasso.get()
            .load(step.stepImagePath)
            .into(holder.binding.stepImage)
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
                        R.id.deleteStep -> {
                            listener.onStepDeleteClicked(step)
                            true
                        }
                        R.id.editStep -> {
                            listener.onEditClicked(step)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {

        }


        fun bind(step: Step) = with(binding) {
            this@StepHolder.step = step
            stepNumber.text = step.id.toString()
            stepContent.text = step.stepContent
            stepOptions.setOnClickListener { popupMenu.show() }
        }

    }

    private object StepDiffCallback : DiffUtil.ItemCallback<Step>() {
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
}