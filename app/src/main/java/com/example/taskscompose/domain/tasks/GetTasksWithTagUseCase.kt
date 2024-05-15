package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) = taskRepository.deleteTask(task)
}