package my.com.testroomdb.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.com.testroomdb.R
import my.com.testroomdb.data.UserViewModel
import my.com.testroomdb.databinding.FragmentListBinding
import my.com.testroomdb.fragments.list.adapter.ListAdapter

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding =  FragmentListBinding.inflate(inflater, container, false)

        val adapter = ListAdapter()
        binding.rvList.adapter = adapter

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllData().observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        binding.addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return binding.root

    }

}