package kaleidot725.michetimer.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.TimerListViewItemBinding

internal class MainTimerListAdapter(timerViewModels : MainTimersViewModel) : RecyclerView.Adapter<MainTimersViewHolder>(), LifecycleOwner {
    private val registry : LifecycleRegistry
    private val timerViewModels : ObservableList<MainTimerViewModel>

    init {
        this.registry = LifecycleRegistry(this)
        this.registry.markState(Lifecycle.State.CREATED)
        this.timerViewModels = timerViewModels.all
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTimersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TimerListViewItemBinding>(layoutInflater, R.layout.timer_list_view_item, parent, false)
        registry.markState(Lifecycle.State.STARTED)
        return MainTimersViewHolder(this, binding)
    }

    override fun onBindViewHolder(holder: MainTimersViewHolder, position: Int) {
        holder.bind(timerViewModels[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        registry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getItemCount(): Int = timerViewModels.count()
    override fun getLifecycle(): Lifecycle = registry
}
