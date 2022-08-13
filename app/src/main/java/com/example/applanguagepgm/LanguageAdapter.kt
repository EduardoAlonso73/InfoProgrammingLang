package com.example.applanguagepgm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            binding.tvDescription.text=language.description
            binding.tvYear.text=language.year
            binding.tvUseLanguage.text=language.useLgn
            binding.cbFavorite.isChecked=language.isFavorite
        }
        Glide.with(mContext)
            .load(language.imgIconLanguage)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.ImgLanguage)
    }
    override fun getItemCount():Int= nLanguage.size


    fun addLanguage(listLanguage: LanguageEntity) {
        nLanguage.add(listLanguage)
        notifyDataSetChanged()
    }

    fun setListLanguage(languageList: MutableList<LanguageEntity>) {
        this.nLanguage=languageList
        notifyDataSetChanged()
    }

    fun updateLanguage(languageEntity: LanguageEntity) {
        val i=nLanguage.indexOf(languageEntity)
        if (i!=-1){
            nLanguage.set(i,languageEntity)
            notifyItemChanged(i)
        }
    }

    fun deleteLang(languageEntity: LanguageEntity) {
        val i=nLanguage.indexOf(languageEntity)
        if (i!=-1){
            nLanguage.removeAt(i)
            notifyItemRemoved(i)
        }
    }

    inner class  ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding =ItemCardLanguageBinding.bind(view)
        fun setListener(language: LanguageEntity){
            binding.root.setOnClickListener{ listener.onClickItem(language.id) }//Componet card_view
            binding.cbFavorite.setOnClickListener{listener.onFavoriteLanguage(language)}
            binding.root.setOnLongClickListener { listener.onDeleteLang(language)
                true}
        }
    }
}

