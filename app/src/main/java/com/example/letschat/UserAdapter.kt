package com.example.letschat

import android.content.Context
import android.content.Intent
import android.util.Config.LOGD
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(
    val userList: ArrayList<User>,
    val onItemClick: (String, String) -> Unit):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val currentUser= userList[position]

        holder.txt_name.text=currentUser.name
        Log.d("VIEW NAME", "onBindViewHolder: ${currentUser.name!!} ${currentUser.uid}")
        holder.itemView.setOnClickListener{

            onItemClick(currentUser.name!!, currentUser.uid!!)
        }
    }

    override fun getItemCount(): Int {
     return userList.size
    }


    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val UserImage=itemView.findViewById<ImageView>(R.id.UserImage)
        val txt_name=itemView.findViewById<TextView>(R.id.txt_name)

    }
}