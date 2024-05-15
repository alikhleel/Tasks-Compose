package com.example.taskscompose.domain.tags

import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Reusable
class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    operator fun invoke(): Flow<List<Task>> = taskRepository.getAllTasks()
}