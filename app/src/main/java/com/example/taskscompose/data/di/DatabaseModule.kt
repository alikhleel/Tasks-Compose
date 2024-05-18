package com.example.taskscompose.data.di

import android.content.Context
import com.example.taskscompose.data.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return TasksDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTaskDao(database: TasksDatabase) = database.taskDao()
}