package ro.upt.ac.chiuitter.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ChiuitDao {

    @Query("SELECT * FROM chiuits")
    fun getAll(): List<ChiuitEntity>

    // TODO 11: Define the insert operation.
    @Insert
    fun insertChiuit(chiuit: ChiuitEntity)

    // TODO 14: Define the delete operation.
    @Delete
    fun deleteChiuit(chiuit: ChiuitEntity)
}