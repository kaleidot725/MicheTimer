package kaleidot725.michetimer.disptimer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kaleidot725.michetimer.*
import kaleidot725.michetimer.databinding.FragmentAddTimerBinding
import kaleidot725.michetimer.databinding.FragmentDispTimerBinding
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.service.TimerRunnerService
import java.lang.Exception

class DispTimerFragment : Fragment() {
    companion object {
        fun create(context : Context, id : Int) : Fragment {
            val fragment = DispTimerFragment().apply {
                val bundle = Bundle().apply {
                    putInt("id", id)
                }
                arguments = bundle
            }
            return fragment
        }
    }

    private var timerId : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_disp_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) {
            throw Exception("arguments null")
        }

        val bundle = arguments as Bundle
        timerId = bundle.getInt("id", -1)

        val viewmodel = ViewModelProviders.of(this, TimerViewModelFactory()).get(DispTimerViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentDispTimerBinding>(view)
        binding?.setVariable(BR.dispTimerViewModel, viewmodel)
        binding?.setLifecycleOwner(this)
    }

    private inner class TimerViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == DispTimerViewModel::class.java) {
                if (dispTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerService == null)
                {
                    throw java.lang.IllegalStateException("timerService is null")
                }
                if (timerRepository == null) {
                    throw IllegalStateException("Timers is null")
                }

                val navigator = dispTimerNavigator as DispTimerNavigator
                val service = timerService as TimerRunnerService
                val repository = timerRepository as TimerRepository
                val timer = repository.findById(timerId) as Timer

                return DispTimerViewModel(navigator, timer, service, repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
