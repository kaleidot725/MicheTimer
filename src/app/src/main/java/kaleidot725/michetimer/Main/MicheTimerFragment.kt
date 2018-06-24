package kaleidot725.michetimer.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.models.ViewModelFactory
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentMicheTimerBinding
import kaleidot725.michetimer.models.Timer

class MicheTimerFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_miche_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this, ViewModelFactory).get(MicheTimerViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentMicheTimerBinding>(view)
        binding?.setVariable(BR.micheTimerViewModel, viewModel)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TimerListAdapter(viewModel.timerViewModels)
        }

        ViewModelFactory.timers?.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Timer>>(){
            override fun onChanged(sender: ObservableList<Timer>?) {
            }

            override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    viewModel.timerViewModels.add(TimerViewModel(ViewModelFactory?.micheTimerNavigator as MicheTimerNavigator, sender.get(positionStart)))
                    recyclerView.adapter.notifyItemInserted(positionStart + 1)
                }
            }

            override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
                if (sender != null) {
                    viewModel.timerViewModels.add(TimerViewModel(ViewModelFactory?.micheTimerNavigator as MicheTimerNavigator, sender.get(positionStart)))
                    recyclerView.adapter.notifyItemInserted(positionStart)
                }
            }

            override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            }

            override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            }
        })
    }
}
