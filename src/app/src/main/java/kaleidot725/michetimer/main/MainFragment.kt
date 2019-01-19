package kaleidot725.michetimer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentMainBinding
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.mainNavigator
import kaleidot725.michetimer.timerRepository
import kaleidot725.michetimer.timerService
import kaleidot725.michetimer.service.TimerRunnerService

class MainFragment() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel = ViewModelProviders.of(this, MicheTimerViewModelFactory).get(MainViewModel::class.java)
        val timersViewModel = ViewModelProviders.of(this, MicheTimerViewModelFactory).get(MainTimersViewModel::class.java)

        val binding = DataBindingUtil.bind<FragmentMainBinding>(view)
        binding?.setVariable(BR.mainViewModel, mainViewModel)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = MainTimerListAdapter(timersViewModel)
        }

        timersViewModel.onRemoveEvent = { i ->
            recyclerView.adapter?.notifyItemRemoved(i)
        }

        timersViewModel.onAddEvent = { i->
            recyclerView.adapter?.notifyItemInserted(i)
            recyclerView.adapter?.notifyItemChanged(i)
        }

        timersViewModel.onChanged = {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private object MicheTimerViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == MainViewModel::class.java) {
                if (mainNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                return MainViewModel(mainNavigator as MainNavigator) as T
            }

            if (modelClass == MainTimersViewModel::class.java) {
                if (mainNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerRepository == null)
                    throw IllegalStateException("Timers is null")

                return MainTimersViewModel(
                        mainNavigator as MainNavigator ,
                        timerService as TimerRunnerService,
                        timerRepository as TimerRepository
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
