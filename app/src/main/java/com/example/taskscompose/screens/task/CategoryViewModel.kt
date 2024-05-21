package com.example.taskscompose.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.domain.tags.GetAllTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel
@Inject constructor(
    private val getAllTagsUseCase: GetAllTagsUseCase
) : ViewModel() {
    val tags: MutableStateFlow<List<Tags>> = MutableStateFlow(emptyList())

    init {
        getAllTags()
    }

    private fun getAllTags() {
        viewModelScope.launch {
            getAllTagsUseCase().collect {
                tags.value = it
            }
        }
    }
}