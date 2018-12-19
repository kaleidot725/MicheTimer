package kaleidot725.michetimer.addtimer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
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

class AddTimerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this, AddTimerViewModelFactory).get(AddTimerViewModel::class.java)
        val nameEdit = view.findViewById<EditText>(R.id.name_edittext)

        val secondSpinner = view.findViewById<Spinner>(R.id.second_spinner)
        val secondAdapter = ArrayAdapter.createFromResource(view.context ,R.array.seconds, android.R.layout.simple_spinner_dropdown_item)
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        secondSpinner.adapter = secondAdapter

        val minuteSpinner = view.findViewById<Spinner>(R.id.minute_spinner)
        val minuteAdapter = ArrayAdapter.createFromResource(view.context, R.array.minutes, android.R.layout.simple_spinner_dropdown_item)
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        minuteSpinner.adapter = minuteAdapter

        val soundSpinner = view.findViewById<Spinner>(R.id.sound_spinner)
        val soundAdapter = ArrayAdapter.createFromResource(view.context, R.array.sounds, android.R.layout.simple_spinner_dropdown_item)
        soundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        soundSpinner.adapter = soundAdapter

        val alarmUrl = view.findViewById<TextView>(R.id.alarm_url)
        alarmUrl.linksClickable = true
        @Suppress("DEPRECATION")
        alarmUrl.text = Html.fromHtml( "<a href=\"https://pocket-se.info/\">Pocket Sound</a>")
        alarmUrl.movementMethod = LinkMovementMethod.getInstance()

        val binding = DataBindingUtil.bind<FragmentAddTimerBinding>(view)
        binding?.setVariable(BR.viewmodel, viewModel)
    }

    private object AddTimerViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == AddTimerViewModel::class.java) {
                if (addTimerNavigator == null)
                    throw IllegalStateException("MicheTimerNavigator is null")

                if (timerRepository == null) {
                    throw IllegalStateException("Timers is null")
                }

                return AddTimerViewModel(addTimerNavigator as AddTimerNavigator, timerRepository as TimerRepository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
