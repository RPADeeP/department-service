package deep.department.dto

import deep.department.model.User

class AddUserToDepartmentDTO (
    var departmentId: String,
    var userId: String,
    var name: String,
    var users: List<User>?,
    var companyToken:String?
)