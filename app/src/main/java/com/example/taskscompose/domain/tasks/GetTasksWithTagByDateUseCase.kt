package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Reusable
class GetTasksWithTagByDateUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(date: String, query: String): UIState<Flow<List<TaskWithTags>>> =
        tasksRepository.getTasksWithTagsByDate(date, query)
}