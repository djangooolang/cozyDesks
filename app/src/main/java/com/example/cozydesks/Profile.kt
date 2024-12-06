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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        var sp = requireContext().getSharedPreferences("CD",Context.MODE_PRIVATE)

        val number = sp.getString("phoneNumber","Ошибка")
        val userName = sp.getString("userName", "Ошибка")

        val userNameTitle: Button = binding.userNameProfile
        userNameTitle.setText(userName)

        userNameTitle.setOnClickListener {
            var message:String? = "Message"
            showCustomDialogBox(title = "Изменить Имя", hint = "Введите новое имя",sp=sp)
            val userName = sp.getString("userName", "Ошибка")
            userNameTitle.setText(userName)
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
            sp.edit().putString("userName",name.toString()).apply()
            Toast.makeText(requireContext(),"Сохранено",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}