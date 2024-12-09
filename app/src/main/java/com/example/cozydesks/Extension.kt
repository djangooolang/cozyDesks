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
import com.example.cozydesks.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore


class Extension : Fragment() {
    private var _binding: FragmentExtensionBinding? = null
    private val binding get() = _binding!!

    private lateinit var db:FirebaseFirestore

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: merrAdapter
    private var userID: String? = ""
    private var myMerr = ArrayList<merrData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtensionBinding.inflate(inflater,container,false)
        binding.createMerrButton.setOnClickListener {
            val intent = Intent(requireContext(),createMer::class.java)
            startActivity(intent)
        }
        binding.searchNewMerr.setOnClickListener {
            val intent = Intent(requireContext(),searchActivity::class.java)
            startActivity(intent)
        }
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userID = requireArguments().getString("ID").toString()
        Toast.makeText(requireContext(),userID,Toast.LENGTH_LONG).show()
        EventChangeListener()

        adapter = merrAdapter(myMerr)
        recyclerView.adapter = adapter




        // Inflate the layout for this fragment
        return binding.root
    }



    private fun EventChangeListener(){

        val idProfile = userID

        db = FirebaseFirestore.getInstance()
        db.collection("merr")
            .get()
            .addOnSuccessListener { result ->
                for (dc in result){
                    var title:String = dc.getString("title").toString()
                    var subtitle:String = dc.getString("subTitle").toString()
                    var city:String = dc.getString("city").toString()
                    var address:String = dc.getString("address").toString()
                    var creatorID:String = dc.getString("creatorID").toString()
                    var linkToGroup:String = dc.getString("linkToGroup").toString()
                    var description:String = dc.getString("description").toString()
                    if(creatorID == userID){
                        val test = merrData(
                            title = title,
                            subTitle = subtitle,
                            city = city,
                            address = address,
                            creatorID = creatorID,
                            linkToGroup = linkToGroup,
                            description = description
                        )

                        var exists = false
                        for (value in myMerr) {
                            if (value == test) {
                                exists = true
                                break
                            }
                        }

                        if (!exists) {
                            myMerr.add(test)
                            adapter.notifyDataSetChanged()
                        }
                    }

                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Неполучилось подключиться, попробуйте позже!", Toast.LENGTH_LONG).show()
            }

    }


}