package com.example.taskscompose.domain.tags

import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class InsertTagListUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {

    suspend operator fun invoke(tagList: List<Tags>) {
        tasksRepository.insertTagList(tagList)
    }
}