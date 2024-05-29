package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetAllTagWithTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(): Flow<List<TagWithTaskLists>> =
        tasksRepository.getTagWithTaskLists()
}