package kaleidot725.michetimer.addtimer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentAddTimerBinding
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.addTimerNavigator
import kaleidot725.michetimer.timerRepository
import java.lang.Exception

class AddTimerFragment : Fragment() {
    companion object {
        val addMode : Int = 0
        val editMode : Int = 1

        fun create(context : Context, mode : Int, id : Int) : Fragment {
            val fragment = AddTimerFragment().apply {
                val bundle = Bundle().apply {
                    putInt("mode", mode)
                    putInt("id", id)
                }
                arguments = bundle
            }
            return fragment
        }
    }

    private var mode : Int = 0
    private var timerId : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) {
            throw Exception("arguments null")
        }

        val bundle = arguments as Bundle
        mode = bundle.getInt("mode", AddTimerFragment.addMode)
        timerId = bundle.getInt("id", -1)

        val viewModel = if (mode == AddTimerFragment.addMode)
            ViewModelProviders.of(this, TimerViewModelFactory()).get(AddTimerViewModel::class.java )
        else
            ViewModelProviders.of(this, TimerViewModelFactory()).get(EditTimerViewModel::class.java)

        val secondSpinner = view.findViewById<Spinner>(R.id.second_spinner)
        val secondAdapter = ArrayAdapter.createFromResource(view.context ,R.array.seconds, android.R.layout.simple_spinner_dropdown_item)
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        secondSpinner.adapter = secondAdapter
        secondSpinner.setSelection(secondAdapter.getPosition(viewModel.second.toString()))

        val minuteSpinner = view.findViewById<Spinner>(R.id.minute_spinner)
        val minuteAdapter = ArrayAdapter.createFromResource(view.context, R.array.minutes, android.R.layout.simple_spinner_dropdown_item)
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        minuteSpinner.adapter = minuteAdapter
        minuteSpinner.setSelection(secondAdapter.getPosition(viewModel.minute.toString()))

        val soundSpinner = view.findViewById<Spinner>(R.id.sound_spinner)
        val soundAdapter = ArrayAdapter.createFromResource(view.context, R.array.sounds, android.R.layout.simple_spinner_dropdown_item)
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        soundSpinner.adapter = soundAdapter
        soundSpinner.setSelection(soundAdapter.getPosition(viewModel.sound))

        val alarmUrl = view.findViewById<TextView>(R.id.alarm_url)
        alarmUrl.linksClickable = true
        @Suppress("DEPRECATION")
        alarmUrl.text = Html.fromHtml( "<a href=\"https://pocket-se.info/\">Pocket Sound</a>")
        alarmUrl.movementMethod = LinkMovementMethod.getInstance()

        val binding = DataBindingUtil.bind<FragmentAddTimerBinding>(view)
        binding?.setVariable(BR.viewmodel, viewModel)
        binding?.setLifecycleOwner(this)
    }

    private inner class TimerViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == AddTimerViewModel::class.java) {
                if (addTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerRepository == null) {
                    throw IllegalStateException("Timers is null")
                }

                val navigator = addTimerNavigator as AddTimerNavigator
                val repository = timerRepository as TimerRepository

                return AddTimerViewModel(navigator, repository) as T
            }
            else if (modelClass == EditTimerViewModel::class.java)
            {
                if (addTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerRepository == null) {
                    throw IllegalStateException("Timers is null")
                }

                val navigator = addTimerNavigator as AddTimerNavigator
                val repository = timerRepository as TimerRepository
                var timer = repository.findById(timerId)
                if (timer == null)
                    throw Exception("timer is null")

                return EditTimerViewModel(navigator, repository, timer) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
