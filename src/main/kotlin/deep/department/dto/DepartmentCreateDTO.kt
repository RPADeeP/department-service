package deep.department.dto

import deep.department.model.User

class DepartmentCreateDTO(
    var name: String,
    var users: ArrayList<User> = ArrayList(),
    var companyToken: String
)