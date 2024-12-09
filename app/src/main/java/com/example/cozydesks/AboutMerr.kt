package com.example.cozydesks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutMerr : AppCompatActivity() {

    private lateinit var title:TextView
    private lateinit var subtitle:TextView
    private lateinit var address:TextView
    private lateinit var description:TextView
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_merr)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var linkToGroup = ""

        title = findViewById(R.id.tItle)
        subtitle = findViewById(R.id.subtitle)
        address = findViewById(R.id.address)
        description = findViewById(R.id.description)
        nextButton = findViewById(R.id.linktogrouptelegram)

        var data = intent.getParcelableExtra<merrData>("data")
        if (data!=null){
            title.text = data.title
            subtitle.text = data.subTitle
            address.text = data.address
            description.text = data.description
            linkToGroup = data.linkToGroup
        }

        nextButton.setOnClickListener {
            var url = Uri.parse(linkToGroup)
            val intent = Intent(Intent.ACTION_VIEW,url)
            intent.setPackage("org.telegram.messenger")
            try {
                startActivity(intent)
            }catch (e:Exception){
                Toast.makeText(this,"Нету ссылки на группы(",Toast.LENGTH_LONG).show()
            }
        }

    }
}