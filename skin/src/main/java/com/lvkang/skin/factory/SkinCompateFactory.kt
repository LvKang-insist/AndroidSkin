package com.lvkang.skin.factory

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.lvkang.skin.inflater.SkinViewInflater
import com.lvkang.skin.wedget.SkinCompatSupportable
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author 345 QQ:1831712732
 * @name SkinCompateFactory
 * @package com.lvkang.skin.factory
 * @time 2020/11/27 23:39
 * @description
 */
class SkinCompateFactory : LayoutInflater.Factory2 {

    private val mSkinHelpers = CopyOnWriteArrayList<WeakReference<SkinCompatSupportable>>()

    private val mSkinCompatViewInflater by lazy { SkinViewInflater() }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return onCreateView(name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = mSkinCompatViewInflater.createView(name, context, attrs)
        if (view is SkinCompatSupportable) {
            mSkinHelpers.add(WeakReference(view))
        }
        return view
    }

    fun applySkin() {
        if (mSkinHelpers.isNotEmpty()) {
            mSkinHelpers.forEach {
                it?.run {
                    get()?.run {
                        applySkin()
                    }
                }
            }
        }
    }
}