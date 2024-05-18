package com.example.taskscompose.domain.tags

import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class InsertNewTagUseCase @Inject constructor(
    private val taskRepository: TasksRepository
) {
    suspend operator fun invoke(tag: Tags) = taskRepository.insertTag(tag)
}