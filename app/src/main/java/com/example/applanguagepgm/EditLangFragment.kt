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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EditLangFragment : Fragment() {
    private  lateinit var mBinding:FragmentEditLangBinding
    private var mActivity:MainActivity?=null
    private  var isEdit:Boolean=false
    private  var mLangEntity:LanguageEntity?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding= FragmentEditLangBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.hide()
        val id=arguments?.getLong(getString(R.string.arg_id),0)

         if(id!=null && id!=0L){
             isEdit=true
             getDataLang(id)
         }else{
             isEdit=false
             mLangEntity=LanguageEntity(nameLanguage ="",year = "", useLgn = "",createBy = "", imgIconLanguage ="",fotoCreateBy = "",
                 infCreator = "",
                 description =""
             );

         }
        with(mBinding){
            btnCancel.setOnClickListener { mActivity?.onBackPressed()/*hideKeyboard()*/ }
            btnSave.setOnClickListener { addLang() }
            //etUrlIconLang.addTextChangedListener {showPreviewImage(mBinding.etUrlIconLang.text.toString(), mBinding.imglUrlIconLang) }
            etlUrlImgCreateBy.addTextChangedListener {showPreviewImage(mBinding.etlUrlImgCreateBy.text.toString(),mBinding.imgUrlImgCreateBy)}
             etNameLang.addTextChangedListener { validInput(mBinding.tilNameLang) }
            etCreateBy.addTextChangedListener { validInput(mBinding.tilCreateBy) }
            etUrlIconLang.addTextChangedListener { validInput(mBinding.tilUrlIconLang)
                showPreviewImage(it.toString(), mBinding.imglUrlIconLang)
            }
            etiInfLanguage.addTextChangedListener { validInput(mBinding.tilUrlIconLang)
            }

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
            etNameLang.setText(mLangEntity.nameLanguage)
            etYear.setText(mLangEntity.year)
            etUsedLang.setText(mLangEntity.useLgn)
            etCreateBy.setText(mLangEntity.createBy)
            etUrlIconLang.setText(mLangEntity.imgIconLanguage)
            etlUrlImgCreateBy.setText(mLangEntity.fotoCreateBy)
            etiInfCreateBy.setText(mLangEntity.infCreator)
            etiInfLanguage.setText(mLangEntity.description)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
       // hideKeyboard()
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
if(  mLangEntity!=null && validInput(mBinding.tiInfLanguage,mBinding.tilCreateBy,mBinding.tilUrlIconLang,mBinding.tilNameLang )) {
    with(mLangEntity!!) {
        nameLanguage = mBinding.etNameLang.text.toString().trim()
        year = mBinding.etYear.text.toString().trim()
        useLgn = mBinding.etUsedLang.text.toString().trim()
        createBy = mBinding.etCreateBy.text.toString().trim()
        imgIconLanguage = mBinding.etUrlIconLang.text.toString().trim()
        fotoCreateBy = mBinding.etlUrlImgCreateBy.text.toString().trim()
        infCreator = mBinding.etiInfCreateBy.text.toString().trim()
        description = mBinding.etiInfLanguage.text.toString().trim()
    }

    doAsync {
        if (isEdit) LanguageApplication.database.languageDao().updateLanguage(mLangEntity!!)
        else mLangEntity!!.id =
            LanguageApplication.database.languageDao().addLanguage(mLangEntity!!)
        uiThread {
            hideKeyboard()

            if (isEdit) {
                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show()
                mActivity?.updateLang(mLangEntity!!)
            } else {
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
                mActivity?.addLanguage(mLangEntity!!)
            }
            mActivity?.onBackPressed()
        }

    }
}
    }

    private fun validInput(vararg  textFields:TextInputLayout): Boolean {
        var isValid = true
        for (textField in textFields){
            if(textField.editText?.text.toString().trim().isEmpty()){
                textField.error=getString(R.string.helper_required)
                isValid=false
            }else textField.error=null
        }
        if (!isValid) Snackbar.make(mBinding.root,"Revise los campos requeridos", Snackbar.LENGTH_SHORT).show()
        return isValid
    }

}