package com.example.taskscompose.domain.tags

import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@Reusable
class GetAllTagsUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    operator fun invoke(): Flow<List<Tags>> = taskRepository.getAllTags()
}