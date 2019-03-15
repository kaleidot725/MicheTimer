package kaleidot725.michetimer.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.ListviewItemTimerBinding

internal class MainTimersAdapter(owner : LifecycleOwner, viewModel : MainViewModel) : RecyclerView.Adapter<MainTimersViewHolder>() {
    private val owner : LifecycleOwner = owner
    private val viewModel : MainViewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTimersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListviewItemTimerBinding>(layoutInflater, R.layout.listview_item_timer, parent, false)
        return MainTimersViewHolder(owner, binding)
    }

    override fun onBindViewHolder(holder: MainTimersViewHolder, position: Int) {
        holder.bind(viewModel.all[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = viewModel.all.count()
}
