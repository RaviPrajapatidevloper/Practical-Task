package com.ravi.practicaltaskmvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravi.practicaltaskmvvm.data.model.RepositoryEntity

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM Repository")
    suspend fun getAllRepositories(): List<RepositoryEntity>

    @Query("UPDATE Repository SET bookmarked = :bookmarked WHERE id = :repositoryId")
    suspend fun updateBookmarkStatus(repositoryId: Int, bookmarked: Boolean)

}