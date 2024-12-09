package com.example.cozydesks

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.cozydesks.databinding.FragmentHomeBinding
import com.example.cozydesks.databinding.FragmentProfileBinding
import com.google.firebase.database.collection.LLRBNode.Color
import org.w3c.dom.Text

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        var sp = requireContext().getSharedPreferences("CD",Context.MODE_PRIVATE)

        val number = sp.getString("phoneNumber","Ошибка")
        val userName = sp.getString("userName", "Имя пользователя")
        val country = sp.getString("country","Название вашего города")

        val userNameTitle: Button = binding.userNameProfile
        val changeCountry: Button = binding.locationProfile
        userNameTitle.setText(userName)
        changeCountry.setText(country)

        userNameTitle.setOnClickListener {
            showCustomDialogBox(title = "Изменить Имя", hint = "Введите новое имя",sp=sp)
            val userName = sp.getString("userName", "Ошибка")
            userNameTitle.setText(userName)
        }

        changeCountry.setOnClickListener {
            showCustomDialogBox(title = "Именить местоположение", hint = "Введите название города", sp = sp)
        }









        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showCustomDialogBox(title: String?,hint: String?,sp: SharedPreferences) {
        var dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_custom_editor)
        var titleD: TextView = dialog.findViewById(R.id.dialogCustomTitle)
        var fieldD: EditText = dialog.findViewById(R.id.dialogCustomTE)
        var cancelButton: Button = dialog.findViewById(R.id.dialogCustomCancelButton)
        var saveButton: Button = dialog.findViewById(R.id.dialogCustomSaveButton)


        titleD.text = title.toString()
        fieldD.hint = hint

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        saveButton.setOnClickListener {
            var name = fieldD.text
            if(title == "Именить местоположение"){
                sp.edit().putString("country",name.toString()).apply()
                Toast.makeText(requireContext(),"Сохранено",Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }else{
                sp.edit().putString("userName",name.toString()).apply()
                Toast.makeText(requireContext(),"Сохранено",Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        dialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}