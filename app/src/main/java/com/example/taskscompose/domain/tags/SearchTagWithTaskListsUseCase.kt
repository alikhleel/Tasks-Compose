package com.example.taskscompose.domain.tags

import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class SearchTagWithTaskListsUseCase @Inject constructor(
    val repository: TasksRepository
) {
    suspend operator fun invoke(date: String, tagName: String, query: String) =
        repository.getCombineSearch(date, query, tagName)

}