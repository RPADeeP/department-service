package deep.department.dto

import deep.department.model.User

class DepartmentCreateDTO(
    var name: String,
    var users: List<User> = listOf(),
    var companyToken: String
)