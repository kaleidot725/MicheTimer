package kaleidot725.michetimer.addtimer

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kaleidot725.michetimer.*
import kaleidot725.michetimer.databinding.FragmentAddTimerBinding
import kaleidot725.michetimer.domain.Timer
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class AddTimerFragment : Fragment() {

    @Inject
    lateinit var navigator : AddTimerNavigator

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerRunnerService : TimerRunnerService

    @field:[Inject Named("EditTimer")] lateinit var editTimer : Timer

    @Inject lateinit var addTimerMode : AddTimerMode

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AddTimerActivity).component.plus(AddTimerFragmentModule()).inject(this)

        val vm =  ViewModelProviders.of(this, AddTimerFragmentViewModelFactory()).get(BaseTimerViewModel::class.java)
        val secondSpinner = view.findViewById<Spinner>(R.id.second_spinner)
        val secondAdapter = ArrayAdapter.createFromResource(view.context ,R.array.seconds, android.R.layout.simple_spinner_dropdown_item)
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        secondSpinner.adapter = secondAdapter
        secondSpinner.setSelection(secondAdapter.getPosition(vm.second.toString()))

        val minuteSpinner = view.findViewById<Spinner>(R.id.minute_spinner)
        val minuteAdapter = ArrayAdapter.createFromResource(view.context, R.array.minutes, android.R.layout.simple_spinner_dropdown_item)
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        minuteSpinner.adapter = minuteAdapter
        minuteSpinner.setSelection(secondAdapter.getPosition(vm.minute.toString()))

        val soundSpinner = view.findViewById<Spinner>(R.id.sound_spinner)
        val soundAdapter = ArrayAdapter.createFromResource(view.context, R.array.sounds, android.R.layout.simple_spinner_dropdown_item)
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        soundSpinner.adapter = soundAdapter
        soundSpinner.setSelection(soundAdapter.getPosition(vm.sound))

        val alarmUrl = view.findViewById<TextView>(R.id.alarm_url)
        alarmUrl.linksClickable = true
        @Suppress("DEPRECATION")
        alarmUrl.text = Html.fromHtml( "<a href=\"https://pocket-se.info/\">Pocket Sound</a>")
        alarmUrl.movementMethod = LinkMovementMethod.getInstance()

        val binding = DataBindingUtil.bind<FragmentAddTimerBinding>(view)
        binding?.setVariable(BR.viewmodel, vm)
        binding?.setLifecycleOwner(this)
    }

    private inner class AddTimerFragmentViewModelFactory() : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            when (addTimerMode.value) {
                AddTimerMode.add -> return (AddTimerViewModel(navigator, timerRepository) as T)
                AddTimerMode.edit -> return (EditTimerViewModel(navigator, timerRepository, editTimer) as T)
                else -> throw Exception("AddTimerMode Unknown")
            }
        }
    }
}
