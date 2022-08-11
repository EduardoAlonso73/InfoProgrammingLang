package com.example.applanguagepgm

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.applanguagepgm.databinding.FragmentEditLangBinding
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.concurrent.thread


class EditLangFragment : Fragment() {
    private  lateinit var mBinding:FragmentEditLangBinding
    private var mActivity:MainActivity?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding= FragmentEditLangBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.hide()

        with(mBinding){
            btnCancel.setOnClickListener {
                mActivity?.onBackPressed()
                hideKeyboard()
            }

            btnSave.setOnClickListener { addLang() }
            etUrlIconLang.addTextChangedListener {showPreviewImage(mBinding.etUrlIconLang.text.toString(), mBinding.imglUrlIconLang) }
            etlUrlImgCreateBy.addTextChangedListener {showPreviewImage(mBinding.etlUrlImgCreateBy.text.toString(),mBinding.imgUrlImgCreateBy)}
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mActivity?.supportActionBar?.show()
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
          ----------- Other Function ------------
  -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */

    private fun showPreviewImage(url:String,intoImg:ImageView) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(intoImg)
    }

    private  fun hideKeyboard(){
        val imm =mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken,0)
        //  imm.hideSoftInputFromWindow(view?.windowToken,0)    // Otro forma de hacerlo

    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
            ----------- Method for BD ------------
    -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */

    private fun addLang() {
        val lang=LanguageEntity(
          nameLanguage = mBinding.etNameLang.text.toString().trim(),
          year = mBinding.etYear.text.toString().trim(),
          useLgn = mBinding.etUsedLang.text.toString().trim(),
          imgIconLanguage = mBinding.etUrlIconLang.text.toString().trim(),
          fotoCreateBy = mBinding.etlUrlImgCreateBy.text.toString().trim(),
          infCreator = mBinding.etiInfCreateBy.text.toString().trim(),
          description = mBinding.etiInfLanguage.text.toString().trim()
        );
        doAsync {
            lang.id=LanguageApplication.database.languageDao().addLanguage(lang)
            uiThread {
                hideKeyboard()
                mActivity?.addLanguage(lang)
                mActivity?.onBackPressed()
        }
        }
    }

}