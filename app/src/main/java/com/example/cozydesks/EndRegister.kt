package com.example.cozydesks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EndRegister : AppCompatActivity() {
    private lateinit var surName: EditText
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var nextNutton: Button
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var sp = getSharedPreferences("CD", Context.MODE_PRIVATE)

        surName = findViewById(R.id.regSurName)
        userName = findViewById(R.id.regUserName)
        password = findViewById(R.id.regPassword)
        nextNutton = findViewById(R.id.regButton)
        progressBar = findViewById(R.id.progressBarReg)

        progressBar.visibility = View.INVISIBLE


        nextNutton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            var name = userName.text.trim().toString()
            var surname = surName.text.trim().toString()
            var psswd = password.text.trim().toString()

            var phoneNumber = sp.getString("Number","Ошибка")

            if (psswd.length>=6){

                var db = Firebase.firestore
                var user = hashMapOf(
                    "surname" to surname,
                    "username" to name,
                    "phoneNumber" to phoneNumber,
                    "password" to psswd
                )
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this,"Ваш аккаунт Успешно создан)", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Ошибка при Регистрации, попробуйте позже", Toast.LENGTH_LONG).show()
                    }
                sp.edit().putString("LoginVeriFF","FullLogin").apply()
                sp.edit().putString("surname",surname).apply()
                sp.edit().putString("userName",name).apply()
                sp.edit().putString("phoneNumber",phoneNumber).apply()
                sp.edit().putString("password",psswd).apply()
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

    }
    override fun onStart() {
        super.onStart()
        var sp = getSharedPreferences("CD", Context.MODE_PRIVATE)
        if(sp.getString("LoginVeriFF","0") == "FullLogin"){
            startActivity(Intent(this,register_Activity::class.java))
        }
    }
}