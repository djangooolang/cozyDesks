package com.example.cozydesks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.cozydesks.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

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






        /*
        *  FullLogin - полностью зарегестрирован и вход успешен!
        *  MiddleLogin - прошел проверку номера!
        *  GuestLogin - Гость!
        *
        *   LoginVeriFF [GuestLogin, MiddleLogin, FullLogin]
        *
        *  */

        if(sp.getString("LoginVeriFF","0") == "FullLogin"){
            replaceFragment(Home())

            binding.bottomNavigationView.setOnItemSelectedListener {

                when(it.itemId){

                    R.id.home -> replaceFragment(Home())
                    R.id.extension -> replaceFragment(Extension())
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