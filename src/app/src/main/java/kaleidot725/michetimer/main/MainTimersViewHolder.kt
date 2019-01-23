package kaleidot725.michetimer.main

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.databinding.TimerListViewItemBinding

class MainTimersViewHolder(owner: LifecycleOwner, binding: TimerListViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val owner : LifecycleOwner = owner
    private val binding : TimerListViewItemBinding = binding

    fun bind (timerViewModel : MainTimerViewModel?) {
        binding.setVariable(BR.timerViewModel, timerViewModel)
        binding.executePendingBindings()
        binding.setLifecycleOwner(owner)
    }
}