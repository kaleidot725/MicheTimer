package kaleidot725.michetimer.MicheTimer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kaleidot725.michetimer.Models.Timer

import kaleidot725.michetimer.R

class MicheTimerFragment : Fragment() {
    private lateinit var values : ArrayList<Timer>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_miche_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        values = ArrayList<Timer>()
        values.add(Timer("One"  ,  60))
        values.add(Timer("Two"  , 120))
        values.add(Timer("Three", 180))

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TimerListAdapter(values)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
