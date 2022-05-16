package deep.department.dto

import deep.department.model.User

class AddUserToDepartmentDTO (
    var name: String,
    var users : List<User>,
    var companyToken: String
)