package kaleidot725.michetimer

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kaleidot725.michetimer.Models.ViewModelFactory
import kaleidot725.michetimer.databinding.FragmentMicheTimerBinding

class MicheTimerFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_miche_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProviders.of(this, ViewModelFactory).get(MicheTimerViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentMicheTimerBinding>(view)
        binding?.micheTimerViewModel = viewModel

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = TimerListAdapter(viewModel.timers)
        }
    }
}
