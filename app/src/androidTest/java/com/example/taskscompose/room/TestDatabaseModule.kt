package com.example.taskscompose.room

import android.content.Context
import androidx.room.Room
import com.example.taskscompose.data.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Provides
    @Named("test_db")
    fun provideDatabaseInMemoryDb(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return Room.inMemoryDatabaseBuilder(
            context, TasksDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}