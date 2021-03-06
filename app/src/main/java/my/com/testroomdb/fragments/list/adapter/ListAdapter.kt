package my.com.testroomdb.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import my.com.testroomdb.R
import my.com.testroomdb.fragments.list.ListFragmentDirections
import my.com.testroomdb.model.User

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var userList = emptyList<User>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val root = view
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtFirstName: TextView = view.findViewById(R.id.txtFirstName)
        val txtLastName: TextView = view.findViewById(R.id.txtLastName)
        val txtAge: TextView = view.findViewById(R.id.txtAge)

    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentUser = userList[position]

        holder.txtId.text = currentUser.id.toString()
        holder.txtFirstName.text = currentUser.firstName
        holder.txtLastName.text = currentUser.lastName
        holder.txtAge.text = currentUser.age.toString()

        holder.root.setOnClickListener {
            //pass user object to update fragment directly
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentUser)
            holder.root.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int { return userList.size }

}