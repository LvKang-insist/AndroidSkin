package com.lvkang.skin.inflater

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import androidx.core.view.ViewCompat
import com.lvkang.skin.SkinManager
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @name SkinCompatViewInflater
 * @package com.lvkang.skin.inflater
 * @author 345 QQ:1831712732
 * @time 2020/11/29 14:41
 * @description
 */
open class SkinViewInflater {

    private val mConstructorArgs = arrayOfNulls<Any>(2)
    private val sClassPrefixList = arrayOf(
        "android.widget.",
        "android.view.",
        "android.webkit."
    )
    private val sConstructorMap: MutableMap<String, Constructor<out View?>> = mutableMapOf()
    private val sConstructorSignature: Array<Class<*>> = arrayOf(
        Context::class.java, AttributeSet::class.java
    )
    private val sOnClickAttrs = intArrayOf(R.attr.onClick)


    fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        var view = createViewFromInflater(context, name, attrs)

        if (view == null) {
            //从自定义的 Inflater 中进行加注
            view = createViewFromTag(context, name, attrs)
        }

        if (view != null) {
            // If we have created a view, check its android:onClick
            checkOnClickListener(view, attrs)
        }
        return view
    }


    private fun createViewFromInflater(context: Context, name: String, attrs: AttributeSet): View? {
        var view: View?
        SkinManager.getInflaters().forEach {
            view = it.createView(context, name, attrs)
            if (view != null) return view
        }
        return null
    }


    private fun createViewFromTag(context: Context, n: String, attrs: AttributeSet): View? {
        var name = n
        if (name == "view") {
            name = attrs.getAttributeValue(null, "class")
        }
        return try {
            mConstructorArgs[0] = context
            mConstructorArgs[1] = attrs
            if (-1 == name.indexOf('.')) {
                for (s in sClassPrefixList) {
                    val view: View? = createViewByPrefix(context, name, s)
                    if (view != null) {
                        return view
                    }
                }
                null
            } else {
                createViewByPrefix(context, name, null)
            }
        } catch (e: Exception) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            null
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null
            mConstructorArgs[1] = null
        }
    }

    @Throws(ClassNotFoundException::class, InflateException::class)
    private fun createViewByPrefix(context: Context, name: String, prefix: String?): View? {
        var constructor: Constructor<out View>? = sConstructorMap[name]
        return try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                val clazz = Class.forName(
                    if (prefix != null) prefix + name else name,
                    false,
                    context.classLoader
                ).asSubclass(View::class.java)
                constructor = clazz.getConstructor(*sConstructorSignature)
                sConstructorMap[name] = constructor
            }
            constructor?.isAccessible = true
            constructor?.newInstance(*mConstructorArgs)
        } catch (e: java.lang.Exception) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            null
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkOnClickListener(view: View, attrs: AttributeSet) {
        val context = view.context
        if (context !is ContextWrapper ||
            Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view)
        ) {
            // Skip our compat functionality if: the Context isn't a ContextWrapper, or
            // the view doesn't have an OnClickListener (we can only rely on this on API 15+ so
            // always use our compat code on older devices)
            return
        }

        val a = context.obtainStyledAttributes(attrs, sOnClickAttrs)
        val handlerName = a.getString(0)
        if (handlerName != null) {
            view.setOnClickListener(DeclaredOnClickListener(view, handlerName))
        }
        a.recycle()
    }

    /**
     * An implementation of OnClickListener that attempts to lazily load a
     * named click handling method from a parent or ancestor context.
     */
    private class DeclaredOnClickListener(
        private val mHostView: View,
        private val mMethodName: String
    ) :
        View.OnClickListener {
        private var mResolvedMethod: Method? = null
        private var mResolvedContext: Context? = null
        override fun onClick(v: View) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.context, mMethodName)
            }
            try {
                mResolvedMethod!!.invoke(mResolvedContext, v)
            } catch (e: IllegalAccessException) {
                throw IllegalStateException(
                    "Could not execute non-public method for android:onClick", e
                )
            } catch (e: InvocationTargetException) {
                throw IllegalStateException(
                    "Could not execute method for android:onClick", e
                )
            }
        }

        private fun resolveMethod(context: Context?, name: String) {
            var context = context
            while (context != null) {
                try {
                    if (!context.isRestricted) {
                        val method = context.javaClass.getMethod(
                            mMethodName,
                            View::class.java
                        )
                        if (method != null) {
                            mResolvedMethod = method
                            mResolvedContext = context
                            return
                        }
                    }
                } catch (e: NoSuchMethodException) {
                    // Failed to find method, keep searching up the hierarchy.
                }
                context = if (context is ContextWrapper) {
                    context.baseContext
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    null
                }
            }
            val id = mHostView.id
            val idText =
                if (id == View.NO_ID) "" else " with id '" + mHostView.context.resources.getResourceEntryName(
                    id
                ) + "'"
            throw IllegalStateException(
                "Could not find method " + mMethodName
                        + "(View) in a parent or ancestor Context for android:onClick "
                        + "attribute defined on view " + mHostView.javaClass + idText
            )
        }
    }
}