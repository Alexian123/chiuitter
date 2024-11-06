package ro.upt.ac.chiuitter.data.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ro.upt.ac.chiuitter.domain.Chiuit
import ro.upt.ac.chiuitter.domain.ChiuitRepository

class FirebaseChiuitStore : ChiuitRepository {

    private val database = FirebaseDatabase.getInstance("https://lab-sma-2024-default-rtdb.europe-west1.firebasedatabase.app").reference.child("chiuits")

    override fun getAll(): Flow<List<Chiuit>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("FirebaseChiuitStore", "getAll:", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val nodeValues = mutableListOf<ChiuitNode>()

                val children = p0.children
                // TODO 15: Iterate through the children, get the node value and
                //  add it to nodeValues.
                for (child in children) {
                    child.getValue(ChiuitNode::class.java)?.let {
                        nodeValues.add(it)
                    }
                }

                val items = nodeValues.map { chiuitNode -> chiuitNode.toDomainModel() }

                trySend(items)
            }

        }
        database.addListenerForSingleValueEvent(listener)

        awaitClose { database.removeEventListener(listener) }
    }

    override fun addChiuit(chiuit: Chiuit) {
        // TODO 16: Insert the object into database - don't forget to use the right model.
        database.push().setValue(chiuit.toFirebaseModel())
    }

    override fun removeChiuit(chiuit: Chiuit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                database.removeEventListener(this)
                Log.e("FirebaseChiuitStore", "removeChiuit:", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val children = p0.children

                // TODO 17: Iterate through the children and find the matching node,
                //  then perform the removal.
                for (child in children) {
                    if (child.getValue(ChiuitNode::class.java)?.timestamp == chiuit.timestamp) {
                        child.ref.removeValue()
                        break
                    }
                }

                database.removeEventListener(this)
            }
        })
    }

    fun Chiuit.toFirebaseModel(): ChiuitNode {
        return ChiuitNode(timestamp, description)
    }

    fun ChiuitNode.toDomainModel(): Chiuit {
        return Chiuit(timestamp, description)
    }

}