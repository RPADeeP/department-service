package deep.department.services

import deep.department.component.dto.AddUserToDepartmentDTO
import deep.department.repository.DepartmentRepository
import deep.department.component.dto.DepartmentCreateDTO
import deep.department.model.Department
import deep.department.model.exception.AlreadyHasNameException
import org.springframework.stereotype.Service

interface DepartmentService {
    fun createDepartment(departmentCreateDTO: DepartmentCreateDTO)
    fun getAllDepartmentsForCompany(companyToken: String) : List<Department>
    fun addUsersToDepartment(addUserToDepartmentDTO: AddUserToDepartmentDTO)
}

@Service
class DepartmentServiceImpl(
    val departmentRepository: DepartmentRepository,
) : DepartmentService {
    override fun createDepartment(departmentCreateDTO: DepartmentCreateDTO) {
        if(departmentRepository.findByNameAndCompanyToken(departmentCreateDTO.name, departmentCreateDTO.companyToken) != null) {
            throw AlreadyHasNameException("That department is created")
        }

        val dep = Department(
            departmentCreateDTO.name,
            listOf(),
            departmentCreateDTO.role,
            departmentCreateDTO.companyToken
        )
        departmentRepository.save(dep)
    }

    override fun getAllDepartmentsForCompany(companyToken: String): List<Department> {
        return departmentRepository.findAllByCompanyToken(companyToken) ?: emptyList()
    }

    override fun addUsersToDepartment(addUserToDepartmentDTO: AddUserToDepartmentDTO) {
        val department = departmentRepository.findByNameAndCompanyToken(addUserToDepartmentDTO.name, addUserToDepartmentDTO.companyToken) ?: return
        department.users = addUserToDepartmentDTO.users
        departmentRepository.save(department)
    }
}