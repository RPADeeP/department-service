package deep.department.controller

import deep.department.dto.AddUserToDepartmentDTO
import deep.department.dto.ChangeNameDepartmentDTO
import deep.department.dto.DeleteUserFromDepartmentDTO
import deep.department.dto.DepartmentCreateDTO
import deep.department.model.Department
import deep.department.service.DepartmentService
import org.springframework.http.HttpHeaders
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

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

    @PostMapping(path=["/delete-user"])
    fun deleteUserFromDepartment(@RequestBody deleteUserFromDepartmentDTO: DeleteUserFromDepartmentDTO): Boolean {
        return departmentService.deleteUserFromDepartment(deleteUserFromDepartmentDTO)
    }

    @PostMapping(path=["/delete/{id}"])
    fun deleteUserFromDepartment(@PathVariable id: String, request: HttpServletRequest) {
        return departmentService.deleteDepartment(id, request.getHeader(HttpHeaders.AUTHORIZATION).split(" ".toRegex()).toTypedArray()[1].trim { it <= ' ' })
    }

    @PostMapping(path=["/change-name"])
    fun changeDepartmentName(@RequestBody changeNameDepartmentDTO: ChangeNameDepartmentDTO, request: HttpServletRequest) {
        return departmentService.changeDepartmentName(changeNameDepartmentDTO, request.getHeader(HttpHeaders.AUTHORIZATION).split(" ".toRegex()).toTypedArray()[1].trim { it <= ' ' })
    }
}