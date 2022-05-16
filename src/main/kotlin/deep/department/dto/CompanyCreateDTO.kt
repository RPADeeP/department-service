package deep.department.dto

import deep.department.model.Department

class CompanyCreateDTO (
    var name: String,
    var departments: List<Department> = listOf()
)