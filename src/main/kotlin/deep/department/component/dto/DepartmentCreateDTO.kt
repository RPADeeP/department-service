package deep.department.component.dto

import deep.department.model.Role

class DepartmentCreateDTO(
    var name: String,
    var role: Role,
    var companyToken: String
)