package com.kalugin1912.todoit.view.newtask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalugin1912.todoit.R
import com.kalugin1912.todoit.databinding.ItemPriorityBinding
import com.kalugin1912.todoit.view.Priority

class PriorityAdapter(
    private val priorities: List<Priority>,
    private val onPriorityClicked: (Priority) -> Unit,
) : RecyclerView.Adapter<PriorityViewHolder>() {

    private var selectedPriority = Priority.LOW

    fun select(priority: Priority) {
        if (priority != selectedPriority) {
            val currentSelectedPriorityIndex = priorities.indexOf(selectedPriority)
            val newSelectedPriorityIndex = priorities.indexOf(priority)
            selectedPriority = priority
            notifyItemChanged(currentSelectedPriorityIndex)
            notifyItemChanged(newSelectedPriorityIndex)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityViewHolder {
        val binding = ItemPriorityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriorityViewHolder(binding, onPriorityClicked)
    }

    override fun getItemCount() = priorities.size

    override fun onBindViewHolder(holder: PriorityViewHolder, position: Int) {
        val priority = priorities[position]
        holder.bind(priority, priority == selectedPriority)
    }
}

class PriorityViewHolder(
    private val itemPriority: ItemPriorityBinding,
    private val onClick: (Priority) -> Unit,
) : RecyclerView.ViewHolder(itemPriority.root) {

    fun bind(priority: Priority, isSelected: Boolean) {
        val resId = when (priority) {
            Priority.HIGH -> R.drawable.high_priority_circle_bg
            Priority.MEDIUM -> R.drawable.medium_priority_circle_bg
            Priority.LOW -> R.drawable.low_priority_circle_bg
        }
        itemPriority.priority.setBackgroundResource(resId)
        itemPriority.priority.isSelected = isSelected
        itemPriority.priority.setOnClickListener {
            onClick(priority)
        }
    }

}
