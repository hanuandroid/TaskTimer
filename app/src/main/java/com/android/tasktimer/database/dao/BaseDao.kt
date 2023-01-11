package com.android.tasktimer.database.dao

import androidx.room.*

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T):Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(obj: T)

     @Insert
     fun saveRecord(obj: T)
    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert( obj: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)


    /*@Query("SELECT COUNT(*) FROM T")
    fun getRowsCount(): Int

    @Query("SELECT * FROM T")
    suspend fun all(): List<T>*/


}
