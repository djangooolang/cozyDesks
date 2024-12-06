package com.example.cozydesks

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.cozydesks.databinding.FragmentHomeBinding
import com.example.cozydesks.databinding.FragmentProfileBinding

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var startTestButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resources = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, resources)

        binding.apply {
            searchView.editText.setOnEditorActionListener(object :TextView.OnEditorActionListener{
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    val text = p0?.text.toString()
                    searchView.hide()
                    searchBar.setText(text)
                    return true
                }

            })
        }


        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.startTestButton.setOnClickListener {
            val intent = Intent(requireContext(),test::class.java)
            startActivity(intent)
        }
    }
}