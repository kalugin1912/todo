package com.kalugin1912.todoit.view.tasks

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.kalugin1912.todoit.R
import com.kalugin1912.todoit.collectWhenUIVisible
import com.kalugin1912.todoit.databinding.FragmentTasksBinding
import com.kalugin1912.todoit.di.ServiceLocator
import com.kalugin1912.todoit.view.Priority
import com.kalugin1912.todoit.view.adapter.MarginItemDecoration
import com.kalugin1912.todoit.view.adapter.NewTaskAdapter
import com.kalugin1912.todoit.view.adapter.TaskAdapter
import com.kalugin1912.todoit.view.adapter.TaskItem
import com.kalugin1912.todoit.view.newtask.NewTaskFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    companion object {
        fun newTasksFragment() = TasksFragment()
    }

    private val tasksViewModel by viewModels<TasksViewModel>(
        factoryProducer = { ServiceLocator.tasksViewModelFactory }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentTasksBinding.bind(view)
        val taskAdapter = TaskAdapter()
        val concatAdapter = ConcatAdapter(
            taskAdapter,
            NewTaskAdapter {
                tasksViewModel.onAddNewTask()
            }
        )

        requireActivity().onBackPressedDispatcher.addCallback(owner = viewLifecycleOwner) {
            if (isEnabled) {
                tasksViewModel.onCloseClicked()
            }
        }

        binding.include.toDoRecyclerView.apply {
            adapter = concatAdapter
            setHasFixedSize(false)
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_between_items)))
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.add_task) {
                tasksViewModel.onAddNewTask()
                true
            } else {
                false
            }
        }

        tasksViewModel.apply {
            tasks
                .map { tasks ->
                    tasks.mapNotNull { task ->
                        val drawable = ContextCompat.getDrawable(
                            requireContext(),
                            when (task.priority) {
                                Priority.HIGH -> R.drawable.high_priority_circle_bg
                                Priority.MEDIUM -> R.drawable.medium_priority_circle_bg
                                Priority.LOW -> R.drawable.low_priority_circle_bg
                            }
                        )
                        if (drawable == null) null else TaskItem(
                            id = task.id,
                            name = task.name,
                            description = task.description,
                            statusDrawable = drawable,
                        )
                    }
                }
                .collectWhenUIVisible(viewLifecycleOwner) { items ->
                    taskAdapter.submitList(items)
                }

            navigationEvent.collectWhenUIVisible(viewLifecycleOwner) { event ->
                when (event) {
                    TasksViewModel.NavigationEvent.NewTask -> openNewTaskScreen()
                    TasksViewModel.NavigationEvent.Close -> requireActivity().finish()
                }
            }
        }

    }

    private fun openNewTaskScreen() {
        parentFragmentManager.commit {
            replace(R.id.main_container, NewTaskFragment.newInstance())
                .addToBackStack(null)
        }
    }

}