package my.com.testroomdb.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.com.testroomdb.R
import my.com.testroomdb.viewmodel.UserViewModel
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

        //add menu
        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete){ deleteAllUsers() }

        return super.onOptionsItemSelected(item)

    }

    private fun deleteAllUsers() {

        //create alert dialog for confirmation
        val builder = AlertDialog.Builder(requireContext())

        //set positive button (Yes) logic
        builder.setPositiveButton("Yes"){ _,_ ->
            userViewModel.deleteAllUser()
            Toast.makeText(requireContext(), "All users are deleted successfully!", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No"){ _,_ ->  }

        builder.setTitle("Delete All Users?")

        builder.setMessage("Are you sure you want to delete all users from database?")

        builder.create().show()

    }

}