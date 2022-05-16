package deep.department.controller

import deep.department.dto.AddUserToDepartmentDTO
import deep.department.dto.DepartmentCreateDTO
import deep.department.model.Department
import deep.department.service.DepartmentService
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/department")
@Secured
class DepartmentController(
    val departmentService: DepartmentService
) {

    @PostMapping(path=["/create"])
    fun create(@RequestBody departmentCreateDTO: DepartmentCreateDTO) {
        return departmentService.createDepartment(departmentCreateDTO)
    }

    @GetMapping(path=["/get-all/{token}"])
    fun getAllDepartments(@PathVariable token: String) : List<Department> {
        return departmentService.getAllDepartmentsForCompany(token)
    }

    @PostMapping(path=["/add-users"])
    fun addUsersToDepartment(@RequestBody addUserToDepartmentDTO: AddUserToDepartmentDTO) {
        return departmentService.addUsersToDepartment(addUserToDepartmentDTO)
    }
}