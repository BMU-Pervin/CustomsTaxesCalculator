package az.squareroot.customstaxescalc

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.squareroot.customstaxescalc.database.datastructure.Carrier
import az.squareroot.customstaxescalc.databinding.ListItemCarrierBinding
import com.google.android.material.card.MaterialCardView

class CarriersAdapter: ListAdapter<Carrier, CarriersAdapter.CarrierViewHolder>(DiffCallback) {

    class CarrierViewHolder(private val binding: ListItemCarrierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carrier: Carrier, selected: Boolean) {
            if (selected) {
                lastCheckedCard = binding.layoutCarrierCard
                binding.layoutCarrierCard.isChecked = selected
            }
            binding.carrier = carrier
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Carrier>() {
        private var lastCheckedCard: MaterialCardView? = null

        override fun areItemsTheSame(oldItem: Carrier, newItem: Carrier): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Carrier, newItem: Carrier): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrierViewHolder {
        return CarrierViewHolder(
            ListItemCarrierBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CarrierViewHolder, position: Int) {
        val carrier = getItem(position)
        val preference = PreferenceManager.getDefaultSharedPreferences(holder.itemView.context)
        val editor = preference.edit()
        Log.i(
            "CarriersAdapter",
            "\n\tcarrierName = ${carrier.name} " +
                    "\n\tcarrierId = ${carrier.id} " +
                    "\n\tposition = $position"
        )
        holder.itemView.findViewById<MaterialCardView>(R.id.layout_carrier_card).setOnClickListener {
            val temp: Int
            val currentCard = it as MaterialCardView
            currentCard.toggle()
            if (currentCard.isChecked) {
                temp = position
                if (lastCheckedCard != null) {
                    lastCheckedCard?.isChecked = false
                }
                lastCheckedCard = currentCard
            } else {
                temp = -1
                lastCheckedCard = null
            }
            editor.putInt("selected_carrier", temp)
            editor.commit()
        }

        holder.bind(carrier, preference.getInt("selected_carrier", -1) == position)
    }
}