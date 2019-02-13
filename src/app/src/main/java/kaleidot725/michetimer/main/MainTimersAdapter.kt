package kaleidot725.michetimer.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.ListviewItemTimerBinding

internal class MainTimersAdapter(viewModel : MainViewModel) : RecyclerView.Adapter<MainTimersViewHolder>(), LifecycleOwner {
    private val registry : LifecycleRegistry
    private val viewModel : MainViewModel

    init {
        this.registry = LifecycleRegistry(this)
        this.registry.markState(Lifecycle.State.CREATED)
        this.viewModel = viewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTimersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListviewItemTimerBinding>(layoutInflater, R.layout.listview_item_timer, parent, false)
        registry.markState(Lifecycle.State.STARTED)
        return MainTimersViewHolder(this, binding)
    }

    override fun onBindViewHolder(holder: MainTimersViewHolder, position: Int) {
        holder.bind(viewModel.all[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        registry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getItemCount(): Int = viewModel.all.count()
    override fun getLifecycle(): Lifecycle = registry
}
