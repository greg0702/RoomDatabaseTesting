package my.com.testroomdb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.com.testroomdb.R
import my.com.testroomdb.databinding.FragmentUpdateBinding
import my.com.testroomdb.model.Address
import my.com.testroomdb.model.User
import my.com.testroomdb.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var userViewModel: UserViewModel

    private val userId by lazy { arguments?.getInt("id", 0) ?: 0 }
    private val firstName by lazy { arguments?.getString("firstName", "") ?: "" }
    private val lastName by lazy { arguments?.getString("lastName", "") ?: "" }
    private val age by lazy { arguments?.getInt("age", 0) ?: 0 }
    private val streetName by lazy { arguments?.getString("streetName", "0") ?: "" }
    private val streetNumber by lazy { arguments?.getInt("streetNumber", 0) ?: 0 }
    private val postCode by lazy { arguments?.getInt("postCode", 0) ?: 0 }
    private val houseNumber by lazy { arguments?.getInt("houseNumber", 0) ?: 0 }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.edtUpdateFirstName.setText(firstName)
        binding.edtUpdateLastName.setText(lastName)
        binding.edtUpdateAge.setText(age.toString())
        binding.edtUpdateStreetName.setText(streetName)
        binding.edtUpdateStreetNumber.setText(streetNumber.toString())
        binding.edtUpdatePostCode.setText(postCode.toString())
        binding.edtUpdateHouseNumber.setText(houseNumber.toString())

        binding.btnUpdate.setOnClickListener { updateItem() }

        //add menu
        setHasOptionsMenu(true)

        return binding.root

    }

    private fun updateItem(){

        val newFirstName = binding.edtUpdateFirstName.text.toString()
        val newLastName = binding.edtUpdateLastName.text.toString()
        val newAge = binding.edtUpdateAge.text.toString()
        val newStreetName = binding.edtUpdateStreetName.text.toString()
        val newStreetNumber = binding.edtUpdateStreetNumber.text.toString()
        val newPostCode = binding.edtUpdatePostCode.text.toString()
        val newHouseNumber = binding.edtUpdateHouseNumber.text.toString()

        val userChecking = inputCheck(newFirstName,newLastName, newAge)

        val addressChecking = addressChecking(newStreetName, newStreetNumber, newPostCode, newHouseNumber)

        if (!userChecking || !addressChecking) {

            // if input check is false, show invalid entry msg
            Toast.makeText(requireContext(), "Invalid Entry detected", Toast.LENGTH_SHORT).show()

        }else{

            //if input check is true, create address object and updated user object
            val newAddress = Address(newStreetName, newStreetNumber.toInt(), newPostCode.toInt(), newHouseNumber.toInt())
            val updatedUser = User(userId, newFirstName, newLastName, newAge.toInt(), newAddress)
            //update data in database
            userViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "User $newFirstName $newLastName is updated successfully!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }

    }

    private fun addressChecking(streetName: String, streetNumber: String, postCode: String, houseNumber: String): Boolean {

        if (streetName.isEmpty() || streetNumber.isEmpty() || postCode.isEmpty() || houseNumber.isEmpty()){ return false }

        if (streetNumber.toInt() < 0 || postCode.toInt() < 1000 || houseNumber.toInt() < 0) { return false }

        return true

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
            val address = Address(streetName, streetNumber, postCode, houseNumber)
            val currentUser = User(userId, firstName, lastName, age, address)
            userViewModel.deleteUser(currentUser)
            Toast.makeText(requireContext(), "User $firstName $lastName is successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No"){ _,_ ->  }

        builder.setTitle("Delete User $firstName?")

        builder.setMessage("Are you sure you want to delete $firstName from database?")

        builder.create().show()

    }

}