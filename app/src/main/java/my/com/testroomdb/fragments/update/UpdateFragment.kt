package my.com.testroomdb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import my.com.testroomdb.R
import my.com.testroomdb.databinding.FragmentUpdateBinding
import my.com.testroomdb.model.User
import my.com.testroomdb.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    //instance of argument in navigation
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.edtUpdateFirstName.setText(args.currentUser.firstName)
        binding.edtUpdateLastName.setText(args.currentUser.lastName)
        binding.edtUpdateAge.setText(args.currentUser.age.toString())

        binding.btnUpdate.setOnClickListener { updateItem() }

        //add menu
        setHasOptionsMenu(true)

        return binding.root

    }

    private fun updateItem(){

        val newFirstName = binding.edtUpdateFirstName.text.toString()
        val newLastName = binding.edtUpdateLastName.text.toString()
        val newAge = binding.edtUpdateAge.text.toString()

        val inputChecking = inputCheck(newFirstName,newLastName, newAge)

        if (!inputChecking) {

            // if input check is false, show invalid entry msg
            Toast.makeText(requireContext(), "Invalid Entry detected", Toast.LENGTH_SHORT).show()

        }else{

            //if input check is true, create updated user object
            val updatedUser = User(args.currentUser.id, newFirstName, newLastName, newAge.toInt())
            //update data in database
            userViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "User $newFirstName $newLastName is updated successfully!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean{

        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()){ return false }

        if (age.toInt() < 1){ return false }

        return true

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { inflater.inflate(R.menu.delete_menu, menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete){ deleteUser() }
        return super.onOptionsItemSelected(item)

    }

    private fun deleteUser() {

        //create alert dialog for confirmation
        val builder = AlertDialog.Builder(requireContext())

        //set positive button (Yes) logic
        builder.setPositiveButton("Yes"){ _,_ ->
            userViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "User ${args.currentUser.firstName} ${args.currentUser.lastName} is successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No"){ _,_ ->  }

        builder.setTitle("Delete User ${args.currentUser.firstName}?")

        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName} from database?")

        builder.create().show()

    }

}