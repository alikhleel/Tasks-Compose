package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.entity.TaskTagCrossRef
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {
        if (task.taskId != null) {
            val tags = taskRepository.getTasksWithTags(task.taskId!!).tags
            val taskWithTags = tags.map {
                TaskTagCrossRef(task.taskId!!, it.name)
            }
            taskRepository.deleteTaskTagCrossRefs(taskWithTags)
        }
        taskRepository.deleteTask(task)
    }
}