package com.bosha.uikit

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bosha.utils.extensions.inflateView
import kotlin.reflect.KClass

@Suppress("FunctionName")
inline fun <reified BINDING : ViewBinding, ITEM> SimpleAdapter(noinline onBind: (BINDING, ITEM) -> Unit): SimpleRvAdapter<BINDING, ITEM> {
    return SimpleRvAdapter(BINDING::class, onBind)
}

class SimpleRvAdapter<BINDING : ViewBinding, ITEM>(
    private val viewBindingType: KClass<BINDING>,
    private val onBind: (BINDING, ITEM) -> Unit
) :
    RecyclerView.Adapter<SimpleViewHolder<BINDING, ITEM>>() {

    var items = listOf<ITEM>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleViewHolder<BINDING, ITEM> {
        val binding = viewBindingType.inflateView(LayoutInflater.from(parent.context), parent)
        return SimpleViewHolder(binding, onBind)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<BINDING, ITEM>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class SimpleViewHolder<BINDING : ViewBinding, ITEM>(
    private val binding: BINDING,
    private val onBind: (BINDING, ITEM) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ITEM) {
        onBind(binding, item)
    }
}