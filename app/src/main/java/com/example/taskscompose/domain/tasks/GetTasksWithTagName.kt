package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.entity.TagWithTaskLists
import com.example.taskscompose.data.entity.TaskWithTags
import com.example.taskscompose.data.model.UIState
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetTasksWithTagNameUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    operator fun invoke(tagName: String): Flow<TagWithTaskLists> =
        taskRepository.getTagsWithTask(tagName)
}