package kaleidot725.michetimer.main

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.*
import kaleidot725.michetimer.Main.TimerViewModels
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.TimerListViewItemBinding

internal class TimerListAdapter(timerViewModels : TimerViewModels) : RecyclerView.Adapter<TimerListViewHolder>(), LifecycleOwner {
    private val registry : LifecycleRegistry
    private val timerViewModels : ObservableList<TimerViewModel>

    init {
        this.registry = LifecycleRegistry(this)
        this.registry.markState(Lifecycle.State.CREATED)
        this.timerViewModels = timerViewModels.all
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TimerListViewItemBinding>(layoutInflater, R.layout.timer_list_view_item, parent, false)
        registry.markState(Lifecycle.State.STARTED)
        return TimerListViewHolder(this, binding)
    }

    override fun onBindViewHolder(holder: TimerListViewHolder, position: Int) {
        holder.bind(timerViewModels[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        registry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getItemCount(): Int = timerViewModels.count()
    override fun getLifecycle(): Lifecycle = registry
}
