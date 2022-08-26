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
                        R.id.delete -> {
                            listener.onStepDeleteClicked(step)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(step)
                            true
                        }
                        else -> false
                    }
                }
            }
        }


        fun bind(step: Step) {
            with(binding) {
                this@StepHolder.step = step
                stepNumber.text = "Шаг " + step.positionInRecipe.toString()
                stepContent.text = step.stepContent
                stepOptions.setOnClickListener { popupMenu.show() }
            }
            //if (binding.stepImage.id == R.drawable.ic_add_image_24dp) binding.cardImage.visibility = ImageView.GONE
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
                //TODO
            }

        } else {
            for (i in fromPosition downTo toPosition + 1) {
                //TODO
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}