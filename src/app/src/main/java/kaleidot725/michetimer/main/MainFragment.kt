package kaleidot725.michetimer.main

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kaleidot725.michetimer.BR
import kaleidot725.michetimer.R
import kaleidot725.michetimer.databinding.FragmentMainBinding
import java.lang.Exception

class MainFragment : Fragment() {

    lateinit var vmFactory : ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        val binding = DataBindingUtil.bind<FragmentMainBinding>(this.view as View)
        binding?.setVariable(BR.mainViewModel, vm)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = MainTimerListAdapter(vm)
        }

        vm.onRemoveEvent = { i ->
            recyclerView.adapter?.notifyItemRemoved(i)
        }

        vm.onAddEvent = { i->
            recyclerView.adapter?.notifyItemInserted(i)
            recyclerView.adapter?.notifyItemChanged(i)
        }

        vm.onChanged = {
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
