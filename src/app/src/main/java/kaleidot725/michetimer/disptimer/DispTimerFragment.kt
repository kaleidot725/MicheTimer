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
import java.lang.Exception

class DispTimerFragment : Fragment() {

    lateinit var vmFactory : ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_disp_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vm = ViewModelProviders.of(this, vmFactory).get(DispTimerViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentDispTimerBinding>(view)
        binding?.setVariable(BR.dispTimerViewModel, vm)
        binding?.setLifecycleOwner(this)
    }
}
