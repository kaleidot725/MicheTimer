package kaleidot725.michetimer.MicheTimer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.R

class TimerListAdapter(array : ArrayList<Timer>) : RecyclerView.Adapter<TimerListAdapter.ViewHolder>() {
    private val array : ArrayList<Timer> = array

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView          : TextView    = itemView.findViewById<TextView>(R.id.name_textview)
        val remainSecondsTextView : TextView    = itemView.findViewById<TextView>(R.id.remain_seconds_textview)
        val optionButton          : Button      = itemView.findViewById<Button>(R.id.option_button)
        val resetButton           : Button      = itemView.findViewById<Button>(R.id.reset_button)
        val runButton             : Button      = itemView.findViewById<Button>(R.id.run_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(kaleidot725.michetimer.R.layout.timer_list_view_item, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text          = array[position].name
        holder.remainSecondsTextView.text = array[position].remainSeconds.toString()
        holder.optionButton.text          = "︙"
        holder.resetButton.text           = "Reset"
        holder.runButton.text             = "Start"
    }

    override fun getItemCount(): Int = array.count()
}
