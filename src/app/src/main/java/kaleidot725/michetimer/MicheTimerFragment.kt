package kaleidot725.michetimer


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kaleidot725.michetimer.Models.ViewModelFactory

interface MicheTimerNavigator {
    fun timerTimeout(name : String)
}

class MicheTimerFragment : Fragment(), MicheTimerNavigator {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModelFactory : ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_miche_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory()
        val viewModel = ViewModelProviders.of(activity as MicheTimerActivity, viewModelFactory).get(MicheTimerViewModel::class.java)
        viewModel.navigator = this

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TimerListAdapter(viewModel.timerList)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun timerTimeout(name: String) {
        val mainHandler = Handler(context?.mainLooper)
        var runnable = Runnable {
            Toast.makeText(context, "$name Timeout!!!", Toast.LENGTH_SHORT).show()
        }
        mainHandler.post(runnable)
    }
}
