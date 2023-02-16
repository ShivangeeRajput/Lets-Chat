package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    private lateinit var rv:RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()

        rv=findViewById(R.id.rv)
        userList= ArrayList()

        adapter= UserAdapter(userList) { name, uId ->
            navigate(name, uId)
        }
        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter

        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
            for(postSnapshot in snapshot.children){
                val currentUser= postSnapshot.getValue(User::class.java)

                if(mAuth.currentUser?.uid != currentUser?.uid ){
                    userList.add(currentUser!!)}
            }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
    // creating and accessing  menu for logging out
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout)
            // logic for logging out
            mAuth.signOut()
            val intent=Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)

        return true
    }

    private fun navigate(name: String, uID: String) {
        val intent=Intent(this@MainActivity,ChatActivity::class.java)
        intent.putExtra("name",name)
        intent.putExtra("uid",uID)
        startActivity(intent)

    }

}