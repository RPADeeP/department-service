package deep.department.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "DEPARTMENT")
data class Department (
    var name: String,
    var users: List<User>,
    var role: Role,
    var companyToken: String
        ) {
    @Id
    var id: ObjectId = ObjectId.get()
}