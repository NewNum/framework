package com.huxh.mvvmdemo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import java.util.*

/**
 * @author zongjin
 * @date 2019/3/23.
 */
class RecyclerViewHolder private constructor(val context: Context, val convertView: View) :
    RecyclerView.ViewHolder(convertView), LayoutContainer {
    private val mViews: SparseArray<View> by lazy { SparseArray() }
    private val objects: HashMap<String, Any> by lazy { HashMap() }

    override val containerView: View?
        get() = itemView

    fun <T> getObject(key: String): T? {
        return if (objects[key] == null) {
            null
        } else {
            objects[key] as T?
        }
    }

    fun addObject(key: String, `object`: Any) {
        objects[key] = `object`
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View> getView(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 通过viewId获取控件
     *
     * @return
     */
    fun <T : View> getView(viewId: Int, clazz: Class<T>): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View> findViewById(viewId: Int): T {
        return getView(viewId)
    }

    /**
     * 通过viewId获取控件
     *
     * @return
     */
    fun <T : View> findViewById(viewId: Int, clazz: Class<T>): T {
        return getView(viewId, clazz)
    }


    /****以下为辅助方法 */

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(viewId: Int, text: String?): RecyclerViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): RecyclerViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    /*   fun setImageUrl(viewId: Int, url: String): RecyclerViewHolder {
           Glide.with(context).load(url).into(getView<View>(viewId) as ImageView)
           return this
       }*/

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): RecyclerViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): RecyclerViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * 关于事件的
     */
    fun setOnClickListener(
        viewId: Int,
        listener: View.OnClickListener
    ): RecyclerViewHolder {
        val view = getView<View>(viewId)
        view.setOnClickListener(listener)
        return this
    }

    interface OnItemClickListener<T> {
        fun onItemClick(data: T, view: View, position: Int)
    }

    companion object {

        fun createViewHolder(context: Context, itemView: View): RecyclerViewHolder {
            return RecyclerViewHolder(context, itemView)
        }

        fun createViewHolder(parent: ViewGroup, layoutId: Int): RecyclerViewHolder {
            return createViewHolder(parent.context, parent,layoutId)
        }

        fun createViewHolder(
            context: Context,
            parent: ViewGroup, layoutId: Int
        ): RecyclerViewHolder {
            val itemView = LayoutInflater.from(context).inflate(
                layoutId, parent,
                false
            )
            return RecyclerViewHolder(context, itemView)
        }
    }

}
