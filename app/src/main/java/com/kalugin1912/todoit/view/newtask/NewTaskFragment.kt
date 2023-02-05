package com.kalugin1912.todoit.view.newtask

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kalugin1912.todoit.R
import com.kalugin1912.todoit.collectWhenUIVisible
import com.kalugin1912.todoit.databinding.FragmentNewTaskBinding
import com.kalugin1912.todoit.di.ServiceLocator.newTaskViewModelFactory
import com.kalugin1912.todoit.view.Priority

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    companion object {
        fun newInstance() = NewTaskFragment()
    }

    private val newTaskViewModel by viewModels<NewTaskViewModel>(
        factoryProducer = { newTaskViewModelFactory }
    )

    private lateinit var binding: FragmentNewTaskBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewTaskBinding.bind(view)
        val priorityAdapter = PriorityAdapter(
            priorities = listOf(
                Priority.LOW,
                Priority.MEDIUM,
                Priority.HIGH,
            ),
            onPriorityClicked = { priority: Priority ->
                newTaskViewModel.changePriority(priority)
            }
        )
        binding.include.colorPicker.apply {
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    margin = resources.getDimensionPixelSize(R.dimen.margin_between_items)
                )
            )
            setHasFixedSize(true)
            itemAnimator = null
            adapter = priorityAdapter
        }
        setOnBackPressedListeners()

        newTaskViewModel.selectedPriority.collectWhenUIVisible(viewLifecycleOwner) { priority ->
            priorityAdapter.select(priority)
        }
    }

    private fun setOnBackPressedListeners() {
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner = viewLifecycleOwner) {
            if (isEnabled) {
                parentFragmentManager.popBackStack()
            }
        }
    }
}