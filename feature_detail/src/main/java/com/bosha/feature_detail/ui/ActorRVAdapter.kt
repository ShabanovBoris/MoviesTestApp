package com.bosha.feature_detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bosha.core_domain.entities.Actor
import com.bosha.feature_detail.R
import com.bosha.feature_detail.databinding.ActorItemBinding

@Deprecated(
    "Для простых адаптеров теперь использую SimpleRvAdapter или расширение SimpleAdapter<B,T>()",
    replaceWith = ReplaceWith("SimpleRvAdapter")
)
class ActorRecyclerAdapter : RecyclerView.Adapter<ActorRecyclerAdapter.ViewHolderDataActor>() {

    var list = listOf<Actor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDataActor {
        val binding = ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderDataActor(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderDataActor, position: Int) {

        holder.binding.tvActorFullname.text = list[position].name

        holder.binding.ivAvatar.load(list[position].imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_round_person)
            error(R.drawable.ic_round_person)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolderDataActor(val binding: ActorItemBinding) : RecyclerView.ViewHolder(binding.root)
}
