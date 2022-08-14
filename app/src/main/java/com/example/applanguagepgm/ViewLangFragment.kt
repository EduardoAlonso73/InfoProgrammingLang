package com.example.applanguagepgm


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.applanguagepgm.databinding.FragmentViewLangBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class ViewLangFragment : Fragment() {
    private var mActivity:MainActivity?=null
    private lateinit var mBinding: FragmentViewLangBinding
    private  var mLangEntity:LanguageEntity?=null
    private var urlInfDeveloper:String=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    SavedInstanceState: Bundle?): View {
        mBinding= FragmentViewLangBinding.inflate(inflater,container,false)
        return  mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.hide()
        mBinding.imgbBack.setOnClickListener { mActivity?.onBackPressed() }
        mBinding.imgDeveloper.setOnClickListener { Toast.makeText(view.context, urlInfDeveloper, Toast.LENGTH_SHORT).show() }

        val id=arguments?.getLong(getString(R.string.arg_id),0)
        if (id != null && id!=0L) {
            getDataLang(id)
        }


    }

    private fun getDataLang(id: Long) {
        doAsync {
            mLangEntity=LanguageApplication.database.languageDao().getDtaLangById(id)
            uiThread {
                mLangEntity.let{setDtLangUI(mLangEntity!!) }
            }
        }

    }

    private fun setDtLangUI(mLangEntity: LanguageEntity){
        with(mBinding){
            tvNameLang.setText(mLangEntity.nameLanguage)
            tvYear.setText(mLangEntity.year)
            tvUseLanguage.setText(mLangEntity.useLgn)
            tvInf.setText(mLangEntity.description)
            tvNamoDeveloper.setText(mLangEntity.createBy)
        }
        urlInfDeveloper=mLangEntity.infCreator
        showPreviewImage(mLangEntity.imgIconLanguage,mBinding.imgLang)
        showPreviewImage(mLangEntity.fotoCreateBy,mBinding.imgDeveloper)

        //etlUrlImgCreateBy.addTextChangedListener {showPreviewImage(mBinding.etlUrlImgCreateBy.text.toString(),mBinding.imgUrlImgCreateBy)}
    }



    private fun showPreviewImage(url:String,intoImg: ImageView) {
        Glide.with(intoImg.context)
            .load(url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(intoImg)
    }

    override fun onDestroy() {
        super.onDestroy()
        // hideKeyboard()
        mActivity?.supportActionBar?.show()
    }

}