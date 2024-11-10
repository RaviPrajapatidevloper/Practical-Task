package com.ravi.practicaltaskmvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class], version = 1, exportSchema = true
)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract fun daoClass(): RepositoryDao
}