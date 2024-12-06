package com.example.cozydesks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.cozydesks.databinding.FragmentLocationBinding
import com.example.cozydesks.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Location.newInstance] factory method to
 * create an instance of this fragment.
 */
class Location : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentLocationBinding? = null
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
        _binding = FragmentLocationBinding.inflate(inflater,container,false)



        val mks : TextView = binding.MKS
        val event : TextView = binding.EVENT
        val agava : TextView = binding.agava
        val wow : TextView = binding.wow


        mks.setOnClickListener{
            val url = Uri.parse("https://yandex.ru/maps/-/CHAPYUlS")
            val intent = Intent(Intent.ACTION_VIEW,url)
            try {
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Ошибка при открытии карты",Toast.LENGTH_LONG).show()

            }
        }
        agava.setOnClickListener{
            val url = Uri.parse("https://yandex.ru/maps/-/CHAP4A4q")
            val intent = Intent(Intent.ACTION_VIEW,url)
            try {
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Ошибка при открытии карты",Toast.LENGTH_LONG).show()

            }
        }
        event.setOnClickListener{
            val url = Uri.parse("https://yandex.ru/maps/-/CHAPYW5Z")
            val intent = Intent(Intent.ACTION_VIEW,url)
            try {
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Ошибка при открытии карты",Toast.LENGTH_LONG).show()

            }
        }
        wow.setOnClickListener{
            val url = Uri.parse("https://yandex.ru/maps/-/CHAP4Y25")
            val intent = Intent(Intent.ACTION_VIEW,url)
            try {
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(requireContext(),"Ошибка при открытии карты",Toast.LENGTH_LONG).show()

            }
        }








        // Inflate the layout for this fragment
        return binding.root
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
         * @return A new instance of fragment Location.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Location().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}