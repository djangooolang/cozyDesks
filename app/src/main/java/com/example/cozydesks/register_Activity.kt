package com.example.cozydesks


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class register_Activity : AppCompatActivity() {


    // User Data
    //private lateinit var surName: EditText
    //private lateinit var userName: EditText
    private lateinit var tgNick: EditText
    private lateinit var phoneNumberET: EditText
    private lateinit var waitText: TextView
    //private lateinit var password: EditText


    // Buttons
    private lateinit var nextButton: Button
    private lateinit var authButton: TextView

    // Other
    private lateinit var progressBar: ProgressBar


    @SuppressLint("CommitPrefEdits")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Initial User Data
        //surName = findViewById(R.id.regSureName)
        //userName = findViewById(R.id.regUserName)
        tgNick = findViewById(R.id.tgNickName)
        phoneNumberET = findViewById(R.id.tgCheckNum)
        //password = findViewById(R.id.passwd1)


        // Initial Buttons Data
        nextButton = findViewById(R.id.tgCheckBtn)
        authButton = findViewById(R.id.tabAuthButton)

        //Initial Other
        progressBar = findViewById(R.id.progressBar)
        waitText = findViewById(R.id.waitLabel)

        progressBar.visibility = View.INVISIBLE
        waitText.visibility = View.INVISIBLE
        nextButton.isEnabled = true
        //surName.hint = "Фамилия"
        //userName.hint = "Имя"
        tgNick.hint = "Введите Никнейм Telegram"
        phoneNumberET.hint = "Введите номер"
        //password.hint = "Придумайте пароль"
        //correctPassword.hint = "Повторите Пароль"







        var sp = getSharedPreferences("CD", MODE_PRIVATE).edit()


        // Logic SignUp Activity

        authButton.setOnClickListener{
            val intent = Intent(this,loginActivity::class.java)
            startActivity(intent)
        }

        nextButton.setOnClickListener {

            //var username: String = userName.text.toString()
            //var surname: String = surName.text.toString()
            var userNick = tgNick.text.trim().toString()
            var phoneNumber = phoneNumberET.text.trim().toString()
            //var psswd1 = password.text.trim().toString()
            //var pssw2: String = correctPassword.text.trim().toString()


            if(phoneNumber.length == 10 && phoneNumber.isNotEmpty()){

                phoneNumber = "+7${phoneNumber}"
                progressBar.visibility = View.VISIBLE
                waitText.visibility = View.VISIBLE
                nextButton.isEnabled = false
                intent.setPackage("org.telegram.messenger")
                Python.start(AndroidPlatform(this))


                val python = Python.getInstance()
                val pyModule = python.getModule("main")
                val url = Uri.parse("https://t.me/hfggdhh_auth_bot")
                val intent = Intent(Intent.ACTION_VIEW,url)

                try {
                    startActivity(intent)
                    GlobalScope.launch(Dispatchers.Main) {
                        var currentResult = withContext(Dispatchers.IO){
                            pyModule.callAttr("main")
                        }

                        var result: String = currentResult.toString()
                        var returnedNUM = result.substring(2,13)
                        var returnedNICK = result.substring(17,result.lastIndex-1)
                        returnedNICK = returnedNICK.trim()
                        returnedNUM = "+${returnedNUM.trim()}"



                        if(currentResult.isNotEmpty()){
                            if(returnedNUM == phoneNumber && returnedNICK == userNick){

                                Toast.makeText(this@register_Activity,"Ушпешно",Toast.LENGTH_LONG).show()

                                sp.putString("LoginVeriFF","MiddleLogin").commit()
                                sp.putString("Number",phoneNumber).commit()
                                startActivity(Intent(this@register_Activity,EndRegister::class.java))

                            }else{
                                nextButton.isEnabled = true
                                progressBar.visibility = View.INVISIBLE
                                waitText.visibility = View.INVISIBLE
                                phoneNumberET.hint = "Ошибка"
                            }
                        }else{
                            waitText.visibility = View.INVISIBLE
                            nextButton.isEnabled = true
                            phoneNumberET.setText("")
                            phoneNumberET.hint = "Ошибка"
                        }
                    }
                }catch (e:Exception){
                    waitText.visibility = View.INVISIBLE
                    nextButton.isEnabled = true
                    phoneNumberET.setText("")
                    Toast.makeText(this@register_Activity,"Ошибка при открытии Telegram", Toast.LENGTH_LONG).show()
                }
            }else{
                waitText.visibility = View.INVISIBLE
                nextButton.isEnabled = true
                phoneNumberET.setText("")
                phoneNumberET.hint = "Тут ошибка ("
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

/*var db = Firebase.firestore
                                    var user = hashMapOf(
                                        "surname" to surname,
                                        "username" to username,
                                        "phoneNumber" to phoneNumber,
                                        "password" to psswd1
                                    )
                                    db.collection("users")
                                        .add(user)
                                        .addOnSuccessListener {


                                            progressBar.visibility = View.INVISIBLE

                                            Toast.makeText(this@register_Activity,"Ваш аккаунт Успешно создан)", Toast.LENGTH_LONG).show()
                                        }
                                        .addOnFailureListener{
                                            Toast.makeText(this@register_Activity,"Ошибка при Регистрации, попробуйте позже", Toast.LENGTH_LONG).show()
                                        }
                                    sp.putString("LCD","-9").apply()
                                    sp.putString("surname",surname).apply()
                                    sp.putString("userName",username).apply()
                                    sp.putString("phoneNumber",phoneNumber).apply()
                                    sp.putString("password",psswd1).apply()*/
