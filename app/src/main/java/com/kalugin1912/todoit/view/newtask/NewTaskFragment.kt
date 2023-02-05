package com.kalugin1912.todoit.view.newtask

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
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
            onPriorityClicked = newTaskViewModel::changePriority
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

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.add_task) {
                newTaskViewModel.save()
                true
            } else {
                false
            }
        }
        binding.include.addTaskTitleEditText.addTextChangedListener { editable ->
            val title = editable?.toString().orEmpty()
            newTaskViewModel.changeTitle(title)
        }
        binding.include.addTaskDescriptionEditText.addTextChangedListener { editable ->
            val description = editable?.toString().orEmpty()
            newTaskViewModel.changeDescription(description)
        }

        newTaskViewModel.apply {
            selectedPriority.collectWhenUIVisible(viewLifecycleOwner) { priority ->
                priorityAdapter.select(priority)
            }
            enableAddButton.collectWhenUIVisible(viewLifecycleOwner) { needShow ->
                val newTaskMenu = binding.toolbar.menu.findItem(R.id.add_task)
                newTaskMenu.isEnabled = needShow
            }
            navigationEvent.collectWhenUIVisible(viewLifecycleOwner) { event ->
                when (event) {
                    NavigationEvent.Close -> parentFragmentManager.popBackStack()
                }
            }
        }

    }

    private fun setOnBackPressedListeners() {
        binding.toolbar.setNavigationOnClickListener {
            newTaskViewModel.close()
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner = viewLifecycleOwner) {
            if (isEnabled) {
                newTaskViewModel.close()
            }
        }
    }
}