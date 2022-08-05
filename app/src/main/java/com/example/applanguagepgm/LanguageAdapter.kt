package com.example.applanguagepgm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applanguagepgm.databinding.ItemCardLanguageBinding

class LanguageAdapter(private var nLanguage:MutableList<LanguageEntity>, private var listener:OnClickListener)
    :RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        val viewItemCard=LayoutInflater.from(mContext).inflate(R.layout.item_card_language,parent,false)
        return  ViewHolder(viewItemCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val language= nLanguage[position]
        with(holder){
            setListener(language)
            binding.tvNameLanguage.text=language.nameLanguage
        }
    }
    override fun getItemCount():Int= nLanguage.size


    fun addLanguage(listLanguage: LanguageEntity) {
        nLanguage.add(listLanguage)
        notifyDataSetChanged()
    }

    inner class  ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding =ItemCardLanguageBinding.bind(view)
        fun setListener(language: LanguageEntity){
            binding.root.setOnClickListener{
                listener.onClick(language)
            }
        }
    }
}

