@file:JvmName("ViewBindingUtil") @file:Suppress("UNCHECKED_CAST", "unused")

package com.shibainu.li.baselib

/**
 * @Description
 * @Author Peter
 * @CreateDate 2021/7/6 15:09
 */

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType


@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }/*.also { binding ->
        if (this is AppCompatActivity && binding is ViewDataBinding) {
            binding.lifecycleOwner = this
        }
    }*/

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(
    layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
): VB = withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, layoutInflater, parent, attachToParent) as VB
}/*.also { binding ->
    if (this is Fragment && binding is ViewDataBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
    }
}*/

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VB =
    inflateBindingWithGeneric(LayoutInflater.from(parent.context), parent, false)

fun <VB : ViewBinding> Any.bindViewWithGeneric(view: View): VB = withGenericBindingClass<VB>(this) { clazz ->
    clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
}/*.also { binding ->
    if (this is Fragment && binding is ViewDataBinding) {
        binding.lifecycleOwner = viewLifecycleOwner
    }
}*/

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    any.allParameterizedType.forEach { parameterizedType ->
        parameterizedType.actualTypeArguments.forEach {
            try {
                return block.invoke(it as Class<VB>)
            } catch (e: NoSuchMethodException) {
            } catch (e: ClassCastException) {
            } catch (e: InvocationTargetException) {
            } catch (e: IllegalAccessException) {
            } catch (e: IllegalArgumentException) {
            }
        }
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}

private val Any.allParameterizedType: List<ParameterizedType>
    get() {
        val genericParameterizedType = mutableListOf<ParameterizedType>()
        var genericSuperclass = javaClass.genericSuperclass
        var superclass = javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericParameterizedType.add(genericSuperclass)
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        return genericParameterizedType
    }

fun formatMapBitmap(resources: Resources, drawableId: Int, widthDimen:Int, heightDimen:Int): Bitmap? {
    val bitmap1 = BitmapFactory.decodeResource(resources, drawableId)
    val bitmap2 = bitmap1.copy(Bitmap.Config.ARGB_8888, true)
    val width = resources.getDimensionPixelSize(widthDimen)
    val height = resources.getDimensionPixelSize(heightDimen)
    return alterSizeBitmap(bitmap2, width, height)
}
fun alterSizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {

    try {
        //计算压缩的比率
        val scaleWidth = newWidth.toFloat() / bitmap.width
        val scaleHeight = newHeight.toFloat() / bitmap.height;
        //获取想要缩放的matrix
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        //获取新的bitmap
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        return null
    }
}