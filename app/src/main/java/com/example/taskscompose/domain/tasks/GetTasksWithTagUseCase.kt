package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetTasksWithTagUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    operator fun invoke(tag: String) = taskRepository.getTagByName(tag)
}