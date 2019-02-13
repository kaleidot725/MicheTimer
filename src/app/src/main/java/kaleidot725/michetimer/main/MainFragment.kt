package kaleidot725.michetimer.main

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.MainFragmentModule
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentMainBinding
import kaleidot725.michetimer.domain.TimerRepository
import kaleidot725.michetimer.domain.TimerRunnerService
import java.lang.Exception
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var timerRepository : TimerRepository

    @Inject
    lateinit var timerRunnerService : TimerRunnerService

    @Inject
    lateinit var navigator : MainNavigator

    lateinit var filter : MainFilter

    lateinit var search : String

    lateinit var viewModel : MainViewModel

    companion object {
        fun create(filter : MainFilter, search : String) : MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString("filter", filter.toString())
            bundle.putString("search", search)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) {
            throw Exception("arguments null")
        }

        this.filter = MainFilter.valueOf(arguments?.getString("filter") as String)
        this.search = arguments?.getString("search") as String

        val activityComponent =  (activity as MainActivity).component
        activityComponent.plus(MainFragmentModule()).inject(this)

        viewModel = ViewModelProviders.of(this, MainViewModelFactory()).get(MainViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentMainBinding>(this.view as View)
        binding?.setVariable(BR.mainViewModel, viewModel)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = MainTimersAdapter(viewModel)
        }

        viewModel.onRemoveEvent = { i ->
            recyclerView.adapter?.notifyItemRemoved(i)
        }

        viewModel.onAddEvent = { i->
            recyclerView.adapter?.notifyItemInserted(i)
            recyclerView.adapter?.notifyItemChanged(i)
        }

        viewModel.onChanged = {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    inner class MainViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == MainViewModel::class.java) {
                return MainViewModel(navigator, timerRunnerService, timerRepository, filter, search) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        }
    }
}
