package com.example.cozydesks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cozydesks.databinding.FragmentExtensionBinding
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_USER_ID = "userID"

class Extension : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var userID: String? = null
    private var _binding: FragmentExtensionBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: merrAdapter
    private var myMerr = ArrayList<merrData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userID = it.getString(ARG_USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtensionBinding.inflate(inflater, container, false)
        binding.createMerrButton.setOnClickListener {
            val intent = Intent(requireContext(), createMer::class.java)
            startActivity(intent)
        }
        binding.searchNewMerr.setOnClickListener {
            val intent = Intent(requireContext(), searchActivity::class.java)
            startActivity(intent)
        }

        //Toast.makeText(requireContext(),userID,Toast.LENGTH_LONG).show()

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        EventChangeListener()

        adapter = merrAdapter(myMerr)
        recyclerView.adapter = adapter

        return binding.root
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("merr")
            .get()
            .addOnSuccessListener { result ->
                for (dc in result) {
                    val title = dc.getString("title").toString()
                    val subtitle = dc.getString("subTitle").toString()
                    val city = dc.getString("city").toString()
                    val address = dc.getString("address").toString()
                    val creatorID = dc.getString("creatorID").toString()
                    val linkToGroup = dc.getString("linkToGroup").toString()
                    val description = dc.getString("description").toString()
                    if (creatorID == userID) {
                        val test = merrData(
                            title = title,
                            subTitle = subtitle,
                            city = city,
                            address = address,
                            creatorID = creatorID,
                            linkToGroup = linkToGroup,
                            description = description
                        )

                        if (!myMerr.contains(test)) {
                            myMerr.add(test)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Не удалось подключиться, попробуйте позже!", Toast.LENGTH_LONG).show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, userID: String) =
            Extension().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_USER_ID, userID)
                }
            }
    }
}
