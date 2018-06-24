package kaleidot725.michetimer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kaleidot725.michetimer.databinding.TimerListViewItemBinding

class TimerListAdapter(array : List<TimerViewModel>) : RecyclerView.Adapter<TimerListAdapter.ViewHolder>(), LifecycleOwner {
    private val registry : LifecycleRegistry
    private val array : List<TimerViewModel>

    init {
        this.registry = LifecycleRegistry(this)
        this.registry.markState(Lifecycle.State.CREATED)
        this.array = array
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<TimerListViewItemBinding>(layoutInflater, R.layout.timer_list_view_item, parent, false)

        registry.markState(Lifecycle.State.STARTED)
        return ViewHolder(this, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(array[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        registry.markState(Lifecycle.State.DESTROYED)
    }

    override fun getItemCount(): Int = array.count()
    override fun getLifecycle(): Lifecycle = registry

    class ViewHolder(owner: LifecycleOwner, binding: TimerListViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val owner : LifecycleOwner = owner
        private val binding : TimerListViewItemBinding = binding

        fun bind (timerViewModel : TimerViewModel?) {
            binding.timerViewModel = timerViewModel
            binding.executePendingBindings()
            binding.setLifecycleOwner(owner)
        }
    }
}
