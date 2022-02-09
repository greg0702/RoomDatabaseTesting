package my.com.testroomdb.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.com.testroomdb.R
import my.com.testroomdb.data.User
import my.com.testroomdb.data.UserViewModel
import my.com.testroomdb.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding =  FragmentAddBinding.inflate(layoutInflater, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnAdd.setOnClickListener { insertDataToDatabase() }

        return binding.root

    }

    private fun insertDataToDatabase() {

        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val age = binding.edtAge.text.toString()

        val inputChecking = inputCheck(firstName, lastName, age)

        if (!inputChecking) {

            // if input check is false, show invalid entry msg
            Toast.makeText(requireContext(), "Invalid Entry detected", Toast.LENGTH_SHORT).show()

        }else{

            //if input check is true, create user object
            val user = User(0, firstName, lastName, age.toInt())
            //add data to database
            userViewModel.addUser(user)
            Toast.makeText(requireContext(), "User $firstName $lastName is added successfully!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean{

        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()){ return false }

        if (age.toInt() < 1){ return false }

        return true

    }

}