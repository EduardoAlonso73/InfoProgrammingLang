package com.example.applanguagepgm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.applanguagepgm.databinding.FragmentEditLangBinding
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
            btnCancel.setOnClickListener { mActivity?.onBackPressed() }
            btnSave.setOnClickListener { addLang() }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mActivity?.supportActionBar?.show()
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
                mActivity?.addLanguage(lang)
                mActivity?.onBackPressed()
        }
        }
    }

}