package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var edt_name:  EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var btnsignup: Button

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()

        edt_name=findViewById(R.id.edt_name)
        edt_email=findViewById(R.id.edt_email)
        edt_password=findViewById(R.id.edt_password)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener {
            val name= edt_name.text.toString()
            val email =edt_email.text.toString()
            val password =edt_password.text.toString()

            signUp(name,email,password)

        }
    }


    private fun signUp(name:String,email:String,password:String){
    //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, code for jumping to home activity
                    //creating function for adding user data to database
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,"Error Ocuured", Toast.LENGTH_SHORT).show()
                }
            }


    }

    //adding  new user data to database
    private fun addUserToDatabase(name:String,email: String,uid:String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }













}