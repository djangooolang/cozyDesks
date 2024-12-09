package com.example.cozydesks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.cozydesks.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        var sp = getSharedPreferences("CD",Context.MODE_PRIVATE)


        var id = ""
        db = Firebase.firestore





        /*
        *  FullLogin - полностью зарегестрирован и вход успешен!
        *  MiddleLogin - прошел проверку номера!
        *  GuestLogin - Гость!
        *
        *   LoginVeriFF [GuestLogin, MiddleLogin, FullLogin]
        *
        *  */

        if(sp.getString("LoginVeriFF","0") == "FullLogin"){

            var phoneNumber = sp.getString("phoneNumber",null)
            var psswd = sp.getString("password",null)
            val fragment2 = Extension()

            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (dc in result){
                        if(dc.getString("password" ) == psswd && dc.getString("phoneNumber") == phoneNumber){
                            id = dc.id
                            sp.edit().putString("ID",id)
                            val bundle = Bundle()
                            bundle.putString("ID",id)
                            fragment2.arguments = bundle
                        }
                    }
                }




            replaceFragment(Home())


            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){

                    R.id.home -> replaceFragment(Home())
                    R.id.extension -> replaceFragment(fragment2)
                    R.id.location -> replaceFragment(Location())
                    R.id.profile -> replaceFragment(Profile())

                    else -> {

                    }
                }
                true

            }
        }else if(sp.getString("LoginVeriFF","0") == "MiddleLogin"){
            startActivity(Intent(this,EndRegister::class.java))

        }else {
            startActivity(Intent(this,register_Activity::class.java))
        }

    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }


}