package com.lvkang.skin.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import com.lvkang.skin.SkinManager
import com.lvkang.skin.factory.SkinCompateFactory
import com.lvkang.skin.obsreve.SkinObserver
import com.lvkang.skin.util.SkinLog
import java.lang.Exception
import java.util.*

/**
 * @name SkinActivityLifecycle
 * @package com.lvkang.skin.app
 * @author 345 QQ:1831712732
 * @time 2020/12/13 21:40
 * @description 监听 Activity 的创建，以实现换肤
 */
class SkinActivityLifecycle : Application.ActivityLifecycleCallbacks {


    private val weekDelegateMap by lazy { WeakHashMap<Context, SkinCompateFactory>() }
    private val weekObserverMap by lazy { WeakHashMap<Context, LazySkinObserver>() }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? SkinCompatActivity)?.run {
        } ?: kotlin.run {
            installLayoutFactory(activity)
            Log.e("---345--->", "111111111111111");
        }

    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        (activity as? SkinCompatActivity)?.run {
        } ?: kotlin.run {
            val lazyObserver = getLazyObserver(activity)
            SkinManager.addSkinObserver(lazyObserver)
        }
    }

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        (activity as? SkinCompatActivity)?.run {
        } ?: kotlin.run {
            val lazyObserver = getLazyObserver(activity)
            SkinManager.removeSkinObserver(lazyObserver)
        }

    }

    private fun installLayoutFactory(context: Context) {
        try {
            //这里不能同通过 LayoutInflaterCompat 设置 factory2
            //因为此时的 activity 已经在 setContentView 方法这种设置过 fractory2 了
            //factory2不允许重复设置，所以这里需要通过反射重新设置
//            val skinFactory = getSkinFactory(context)
//            LayoutInflaterCompat.setFactory2((context as Activity).layoutInflater, skinFactory)
            val layoutInflater = LayoutInflater.from(context)
            val inflaterCompat = LayoutInflaterCompat::class.java
            val inflater = LayoutInflater::class.java

            val sCheckedFiled = inflaterCompat.getDeclaredField("sCheckedField")
            sCheckedFiled.isAccessible = true
            sCheckedFiled.set(layoutInflater, false)

            val skinFactory = getSkinFactory(context)
            val mFactory = inflater.getDeclaredField("mFactory")
            val mFactory2 = inflater.getDeclaredField("mFactory2")
            mFactory.isAccessible = true
            mFactory2.isAccessible = true

            mFactory.set(layoutInflater, skinFactory)
            mFactory2.set(layoutInflater, skinFactory)

        } catch (e: Exception) {
            e.printStackTrace()
            SkinLog.log("A factory has already been set on this LayoutInflater")
        }
    }

    private fun getLazyObserver(context: Context): LazySkinObserver {
        var lazySkinObserver = weekObserverMap[context]
        if (lazySkinObserver == null) {
            lazySkinObserver = LazySkinObserver(context)
            weekObserverMap[context] = lazySkinObserver
        }
        return lazySkinObserver
    }

    private fun getSkinFactory(context: Context): SkinCompateFactory {
        var skinCompateFactory = weekDelegateMap[context]
        if (skinCompateFactory == null) {
            skinCompateFactory = SkinCompateFactory()
            weekDelegateMap[context] = skinCompateFactory
        }
        return skinCompateFactory
    }


    inner class LazySkinObserver(val context: Context) : SkinObserver {

        override fun applySkin() = updataSkin()

        private fun updataSkin() {
            val skinFactory = getSkinFactory(context)
            skinFactory.applySkin()
        }
    }
}