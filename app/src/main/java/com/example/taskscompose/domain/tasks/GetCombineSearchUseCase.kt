package com.example.taskscompose.domain.tasks

import com.example.taskscompose.data.repository.TasksRepository
import dagger.Reusable
import javax.inject.Inject


@Reusable
class GetCombineSearchUseCase @Inject constructor(
    private val repository: TasksRepository
) {
    operator fun invoke(query: String, date: String = "", tagName: String = "") =
        repository.getCombineSearch(
            query = query, date = date, tag = tagName
        )
}