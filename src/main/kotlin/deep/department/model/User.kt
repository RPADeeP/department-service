package deep.department.model

import org.bson.types.ObjectId

class User (
    var id: ObjectId,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var code: String,
    var role: Role,
    var companyToken: String? = ""
)