package com.example.taskscompose.room

import androidx.test.filters.SmallTest
import com.example.taskscompose.data.dao.TaskDao
import com.example.taskscompose.data.database.TasksDatabase
import com.example.taskscompose.data.entity.Tags
import com.example.taskscompose.data.entity.Task
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@SmallTest
class TaskDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: TasksDatabase

    private lateinit var dao: TaskDao

    val task = Task(1, "Task 1", "Description 1", "", "", "", "")
    val tag = Tags("Tag 1", "Color", "Border")

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.taskDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTask() = runTest {
        dao.addTask(task)
        val result = dao.getAllTasks().first()
        assert(result == listOf(task))
    }

    @Test
    fun deleteTask() = runTest {
        dao.addTask(task)
        dao.deleteTask(task)
        val result = dao.getAllTasks().first()
        assert(result.isEmpty())
    }

    @Test
    fun getAllTasks() = runTest {
        val task2 = task.copy(taskId = 2, title = "Task 2")
        dao.addTask(task)
        dao.addTask(task2)
        val result = dao.getAllTasks().first()

        assert(result == listOf(task, task2))
    }

    @Test
    fun insertTag() = runTest {
        dao.upsertTag(tag)
        val result = dao.getAllTags().first()
        assert(result == listOf(tag))
    }

    @Test
    fun deleteTag() = runTest {
        dao.upsertTag(tag)
        dao.deleteTag(tag)
        val result = dao.getAllTags().first()
        assert(result.isEmpty())
    }


    @Test
    fun getAllTags() = runTest {
        val tag2 = tag.copy(name = "Tag 2")
        dao.upsertTag(tag)
        dao.upsertTag(tag2)
        val result = dao.getAllTags().first()
        assert(result == listOf(tag, tag2))
    }
    

}