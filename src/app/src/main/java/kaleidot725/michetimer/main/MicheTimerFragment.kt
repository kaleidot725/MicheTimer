package kaleidot725.michetimer.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentMicheTimerBinding
import kaleidot725.michetimer.repository.*
import kaleidot725.michetimer.repository.TimerRepositoryJson
import kaleidot725.michetimer.service.TimerRunnerService

class MicheTimerFragment() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_miche_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val micheTimerViewModel = ViewModelProviders.of(this, MicheTimerViewModelFactory).get(MicheTimerViewModel::class.java)
        val timerViewModels = ViewModelProviders.of(this, MicheTimerViewModelFactory).get(TimerViewModels::class.java)

        val binding = DataBindingUtil.bind<FragmentMicheTimerBinding>(view)
        binding?.setVariable(BR.micheTimerViewModel, micheTimerViewModel)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TimerListAdapter(timerViewModels)
        }

        timerViewModels.onRemoveEvent = { i, vm -> recyclerView.adapter.notifyItemRemoved(i) }
        timerViewModels.onAddEvent = { i, vm -> recyclerView.adapter.notifyItemInserted(i) }
    }

    private object MicheTimerViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            if (modelClass == MicheTimerViewModel::class.java)
            {
                if (micheTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                return MicheTimerViewModel(micheTimerNavigator as MicheTimerNavigator) as T
            }

            if (modelClass == TimerViewModels::class.java) {
                if (micheTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerRepository == null)
                    throw IllegalStateException("Timers is null")

                return TimerViewModels(
                        micheTimerNavigator as MicheTimerNavigator ,
                        timerService as TimerRunnerService,
                        timerRepository as TimerRepositoryJson
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
