package kaleidot725.michetimer.main

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.databinding.TimerListViewItemBinding
import android.R.menu
import androidx.appcompat.widget.PopupMenu
import android.view.MenuInflater
import android.view.View
import kaleidot725.michetimer.R


class TimerListViewHolder(owner: LifecycleOwner, binding: TimerListViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val owner : LifecycleOwner = owner
    private val binding : TimerListViewItemBinding = binding

    fun bind (timerViewModel : TimerViewModel?) {
        binding.setVariable(BR.timerViewModel, timerViewModel)
        binding.executePendingBindings()
        binding.setLifecycleOwner(owner)
    }
}