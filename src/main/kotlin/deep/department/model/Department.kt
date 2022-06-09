package deep.department.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "DEPARTMENT")
data class Department(
    var name: String,
    var users: ArrayList<User>,
    var companyToken: String
    ){
    @Id
    var id: String = ObjectId.get().toString()
}