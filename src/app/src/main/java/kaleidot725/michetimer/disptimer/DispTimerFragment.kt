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
import kaleidot725.michetimer.databinding.FragmentDispTimerBinding
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import javax.inject.Inject
import javax.inject.Named

class DispTimerFragment : Fragment() {

    @Inject lateinit var timerRepository : TimerRepository

    @Inject lateinit var timerRunnerService : TimerRunnerService

    @Inject lateinit var navigator : DispTimerNavigator

    @field:[Inject Named("DispTimer")] lateinit var dispTimer : Timer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_disp_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as DispTimerActivity).component.plus(DispTimerFragmentModule()).inject(this)

        val vm = ViewModelProviders.of(this, DispTimerViewModelFactory()).get(DispTimerViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentDispTimerBinding>(view)
        binding?.setVariable(BR.dispTimerViewModel, vm)
        binding?.setLifecycleOwner(this)
    }

    private inner class DispTimerViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DispTimerViewModel(navigator, dispTimer, timerRunnerService, timerRepository) as T
        }
    }
}
