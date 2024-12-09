package com.example.cozydesks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class merrAdapter(var mList:List<merrData>):
    RecyclerView.Adapter<merrAdapter.merrViewHolder>() {

    inner class merrViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.merrItemTitle)
        val subTitle:TextView = itemView.findViewById(R.id.merrItemSubTitle)
        val address:TextView = itemView.findViewById(R.id.merrItemAddress)
        val city:TextView = itemView.findViewById(R.id.merrItemCity)
    }

    var onItemClick : ((merrData) -> Unit)? = null

    fun setFilteredList(mList: List<merrData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): merrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.merr_item,parent,false)
        return  merrViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: merrViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.subTitle.text = mList[position].subTitle
        holder.address.text = mList[position].address
        holder.city.text = mList[position].city

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(mList[position])
        }

    }
}