package deep.department.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "COMPANY")
data class Company (
    var name: String,
    var departments:  List<Department>,
        ) {
    @Id
    var id: String = ObjectId.get().toString()
}