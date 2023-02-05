package com.kalugin1912.todoit.view.newtask

import android.os.Build
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
import com.kalugin1912.todoit.view.Task

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    companion object {

        private const val TASK_KEY = "task_key"

        private val Bundle.task: Task?
            get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getParcelable(TASK_KEY, Task::class.java)
            } else {
                getParcelable(TASK_KEY)
            }

        private fun Bundle.putTask(task: Task) = putParcelable(TASK_KEY, task)

        fun newInstance(task: Task? = null) = NewTaskFragment().apply {
            arguments = Bundle().apply {
                if (task != null) {
                    putTask(task)
                }
            }
        }
    }

    private val newTaskViewModel by viewModels<NewTaskViewModel>(
        factoryProducer = { newTaskViewModelFactory }
    )

    private lateinit var binding: FragmentNewTaskBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewTaskBinding.bind(view)

        val priorityAdapter = PriorityAdapter(
            priorities = listOf(
                Priority.LOW,
                Priority.MEDIUM,
                Priority.HIGH,
            ),
            onPriorityClicked = newTaskViewModel::changePriority
        )

        val task = arguments?.task
        if (task != null) {
            newTaskViewModel.setTaskId(task.id)
            fillFields(task)
        }

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

        binding.include.status.setOnClickListener {
            newTaskViewModel.onStatusClicked()
        }

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
            isCompleted.collectWhenUIVisible(viewLifecycleOwner) { isCompleted ->
                binding.include.status.isSelected = isCompleted
                val statusRes = if (isCompleted) R.string.status_completed else R.string.status_in_progress
                binding.include.status.setText(statusRes)
            }
            isUpdate.collectWhenUIVisible(viewLifecycleOwner) { isUpdate ->
                binding.title.setText(
                    if (isUpdate) R.string.new_task_screen_update_title else R.string.new_task_screen_title
                )
                val deleteMenuItem = binding.toolbar.menu.findItem(R.id.delete_task)
                deleteMenuItem.isVisible = isUpdate
            }
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

    private fun fillFields(task: Task) {
        binding.include.addTaskTitleEditText.setText(task.name)
        binding.include.addTaskDescriptionEditText.setText(task.description)

        newTaskViewModel.changeTitle(task.name)
        newTaskViewModel.changeDescription(task.description)
        newTaskViewModel.changePriority(task.priority)
        newTaskViewModel.changeCompletion(task.isCompleted)
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