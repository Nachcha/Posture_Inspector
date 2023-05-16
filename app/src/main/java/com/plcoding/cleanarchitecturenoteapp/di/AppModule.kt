package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.StudentDatabase
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository.ReportRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository.StandStillDataRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository.StudentRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository.WalkingDataRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.ReportRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.WalkingDataRepository
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
    fun provideStandStillDataRepository(db: StudentDatabase): StandStillDataRepository {
        return StandStillDataRepositoryImpl(db.standStillDataDao)
    }

    @Provides
    @Singleton
    fun provideWalkingDataRepository(db: StudentDatabase): WalkingDataRepository {
        return WalkingDataRepositoryImpl(db.walkingDataDao)
    }

    @Provides
    @Singleton
    fun provideReportRepository(db: StudentDatabase): ReportRepository {
        return ReportRepositoryImpl(db.reportDao)
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

    @Provides
    @Singleton
    fun provideReportUseCases(repository: ReportRepository): ReportUseCases {
        return ReportUseCases(
            getReportsByType = GetReportsByType(repository),
            addReport = AddReport(repository),
            updateReport = UpdateReport(repository),
            deleteReport = DeleteReport(repository)
        )
    }

    @Provides
    @Singleton
    fun provideStandStillDataUseCases(repository: StandStillDataRepository): StandStillUseCases {
        return StandStillUseCases(
            addStandStillData = AddStandStillData(repository),
            addStandStillDataList = AddStandStillDataList(repository),
            deleteStandStillData = DeleteStandStillData(repository),
            getStandStillDataList = GetStandStillDataList(repository),
        )
    }

    @Provides
    @Singleton
    fun provideWalkingDataUseCases(repository: WalkingDataRepository): WalkingUseCases {
        return WalkingUseCases(
            addWalkingData = AddWalkingData(repository),
            addWalkingDataList = AddWalkingDataList(repository),
            deleteWalkingData = DeleteWalkingData(repository),
            getWalkingDataList = GetWalkingDataList(repository),
        )
    }
}