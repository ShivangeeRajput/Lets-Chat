package com.example.letschat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var btnlogin: Button
    private lateinit var tv1: TextView
    private lateinit var btnsignup: Button

    private lateinit var mAuth : FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance()

        edt_email=findViewById(R.id.edt_email)
        edt_password=findViewById(R.id.edt_password)
        btnlogin=findViewById(R.id.btnlogin)
        tv1=findViewById(R.id.tv1)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)

            }
        btnlogin.setOnClickListener{
            val email=edt_email.text.toString()
            val password=edt_password.text.toString()

            login(email,password);

        }
        }

           private fun login(email:String,password:String){
           // logic of logging user
               mAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(this) { task ->
                       if (task.isSuccessful) {
                           // Code for logging in user
                           val intent=Intent(this@Login,MainActivity::class.java)
                           finish()
                           startActivity(intent)

                       } else {
                           Toast.makeText(this@Login,"User does not exist",Toast.LENGTH_SHORT).show()

                       }
                   }

           }



    }
