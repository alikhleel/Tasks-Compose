package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Reusable
class GetAllTasksUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    operator fun invoke(): UIState<Flow<List<Task>>> = taskRepository.getAllTasks()
}