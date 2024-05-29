package com.example.taskscompose.screens.task

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.domain.tasks.GetAllTagWithTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel
@Inject constructor(
    private val getAllTagWithTasksUseCase: GetAllTagWithTasksUseCase
) : ViewModel() {
    val tags: MutableStateFlow<List<Pair<Tags, Int>>> = MutableStateFlow(emptyList())


    suspend fun getAllTags() {

        getAllTagWithTasksUseCase().collect { data ->
            val tagLists = mutableListOf<Pair<Tags, Int>>()
            for (tag in data) {
                Log.d("CategoryViewModel", "forEach: ${tag.tag}")
                tagLists.add(Pair(tag.tag, tag.tasks.size))
            }
            tags.value = tagLists

        }
    }
}