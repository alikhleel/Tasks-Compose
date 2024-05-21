package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Reusable
class SortTasksByDate @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(date: String): UIState<Flow<List<Task>>> = tasksRepository.sortTasksByTag(date)
}