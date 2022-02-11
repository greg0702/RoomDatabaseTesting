package my.com.testroomdb.fragments.add

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import my.com.testroomdb.R
import my.com.testroomdb.model.User
import my.com.testroomdb.viewmodel.UserViewModel
import my.com.testroomdb.databinding.FragmentAddBinding
import my.com.testroomdb.model.Address

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
        val streetName = binding.edtStreetName.text.toString()
        val streetNumber = binding.edtStreetNumber.text.toString()
        val postCode = binding.edtPostCode.text.toString()
        val houseNumber = binding.edtHouseNumber.text.toString()

        val userChecking = inputCheck(firstName, lastName, age)

        val addressChecking = addressChecking(streetName, streetNumber, postCode, houseNumber)

        if (!userChecking || !addressChecking) {

            // if input check is false, show invalid entry msg
            Toast.makeText(requireContext(), "Invalid Entry detected", Toast.LENGTH_SHORT).show()

        }else{


            lifecycleScope.launch {
                //if input check is true, create address and user object
                val address = Address(streetName, streetNumber.toInt(), postCode.toInt(), houseNumber.toInt())
                val user = User(0,firstName, lastName, age.toInt(), address,getBitmap())
                //add data to database
                userViewModel.addUser(user)
                Toast.makeText(requireContext(), "User $firstName $lastName is added successfully!", Toast.LENGTH_SHORT).show()
                //navigate back
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }

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

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}