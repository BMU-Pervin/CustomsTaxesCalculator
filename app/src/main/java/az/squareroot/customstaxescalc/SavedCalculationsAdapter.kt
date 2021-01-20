package az.squareroot.customstaxescalc

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.squareroot.customstaxescalc.database.datastructure.SavedCalculation
import az.squareroot.customstaxescalc.databinding.ListItemSavedCalculationBinding

class SavedCalculationsAdapter(private val itemClickListener: ItemClickListener):
    ListAdapter<SavedCalculation, SavedCalculationsAdapter.SavedCalculationViewHolder>(DiffCallback) {

    class SavedCalculationViewHolder(private val binding: ListItemSavedCalculationBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(savedCalculation: SavedCalculation) {
                binding.calculation = savedCalculation
                binding.executePendingBindings()
            }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SavedCalculation>() {
        override fun areItemsTheSame(oldItem: SavedCalculation, newItem: SavedCalculation): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SavedCalculation, newItem: SavedCalculation): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCalculationViewHolder {
        return SavedCalculationViewHolder(
            ListItemSavedCalculationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedCalculationViewHolder, position: Int) {
        val savedCalculation = getItem(position)
        holder.itemView.findViewById<Button>(R.id.button_delete).setOnClickListener {
            itemClickListener.onClick(savedCalculation.id)
        }
        holder.bind(savedCalculation)
    }
}