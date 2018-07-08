package kaleidot725.michetimer.EditTimer

import android.icu.text.NumberFormat
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import kaleidot725.michetimer.R

class EditTimerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hourPicker = view.findViewById<NumberPicker>(R.id.hour_picker_value)
        hourPicker.minValue = 0
        hourPicker.maxValue = 24
        hourPicker.setFormatter (object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                return value.toString().padStart(2, '0')
            }
        })

        var minutePicker = view.findViewById<NumberPicker>(R.id.minute_picker_value)
        minutePicker.minValue = 0
        minutePicker.maxValue = 60
        minutePicker.setFormatter (object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                return value.toString().padStart(2, '0')
            }
        })

        var secondPicker = view.findViewById<NumberPicker>(R.id.second_picker_value)
        secondPicker.minValue = 0
        secondPicker.maxValue = 60
        secondPicker.setFormatter (object : NumberPicker.Formatter {
            override fun format(value: Int): String {
                return value.toString().padStart(2, '0')
            }
        })
    }
}
