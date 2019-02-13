package kaleidot725.michetimer.main

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.databinding.ListviewItemTimerBinding

class MainTimersViewHolder(owner: LifecycleOwner, binding: ListviewItemTimerBinding) : RecyclerView.ViewHolder(binding.root) {
    private val owner : LifecycleOwner = owner
    private val binding : ListviewItemTimerBinding = binding

    fun bind (timerViewModel : MainTimerViewModel?) {
        binding.setVariable(BR.timerViewModel, timerViewModel)
        binding.executePendingBindings()
        binding.setLifecycleOwner(owner)
    }
}