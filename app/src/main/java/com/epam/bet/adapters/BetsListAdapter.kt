package com.epam.bet.adapters



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.epam.bet.databinding.BetListItemBinding
import com.epam.bet.entities.Bet
import com.epam.bet.interfaces.RecyclerViewClickListener



class BetsListAdapter(betList: List<Bet?>, private val listener: RecyclerViewClickListener): Adapter<BetsListAdapter.BetsViewHolder>() {
    private var betList: List<Bet?> = betList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetsViewHolder {
        val charBinding = BetListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return BetsViewHolder(charBinding, listener)
    }

    override fun onBindViewHolder(holder: BetsViewHolder, position: Int) {
        var bet: Bet? = betList[position]

        holder.bind(bet)


    }


    override fun getItemCount(): Int {
        return betList.size;
    }


    class BetsViewHolder(private val itemBinding:BetListItemBinding, private val listener: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemBinding.root) {
        init{
            itemBinding.BetLayout.setOnClickListener{
                listener.onRecyclerViewItemClickListener(itemBinding.BetLayout, adapterPosition)
            }

        }

        fun bind(bet: Bet?) {
            itemBinding.BetName.text = bet?.name
        }
    }

}