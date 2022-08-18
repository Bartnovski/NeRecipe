package ru.netology.nerecipe.utils.touch_helper

interface RecipeTouchHelperAdapter {

    fun onItemMove(fromPosition: Int,toPosition: Int)
    fun onItemDismiss(position: Int)

}