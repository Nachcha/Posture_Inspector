package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.StudentDatabase
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository.StudentRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStudentDatabase(app: Application): StudentDatabase {
        return Room.databaseBuilder(
            app,
            StudentDatabase::class.java,
            StudentDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(db: StudentDatabase): StudentRepository {
        return StudentRepositoryImpl(db.studentDao)
    }

    @Provides
    @Singleton
    fun provideStudentUseCases(repository: StudentRepository): StudentUseCases {
        return StudentUseCases(
            getStudents = GetStudents(repository),
            deleteStudent = DeleteStudent(repository),
            addStudent = AddStudent(repository),
            getStudent = GetStudent(repository),
        )
    }
}