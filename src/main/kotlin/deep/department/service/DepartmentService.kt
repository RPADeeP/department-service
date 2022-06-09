package deep.department.service

import deep.department.dto.*
import deep.department.repository.DepartmentRepository
import deep.department.model.Department
import deep.department.model.exception.AlreadyHasNameException
import org.apache.commons.lang.StringUtils
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException

interface DepartmentService {
    fun createDepartment(departmentCreateDTO: DepartmentCreateDTO)
    fun getAllDepartmentsForCompany(companyToken: String) : List<Department>
    fun addUsersToDepartment(addUserToDepartmentDTO: AddUserToDepartmentDTO)
    fun deleteUserFromDepartment(deleteUserFromDepartmentDTO: DeleteUserFromDepartmentDTO): Boolean
    fun deleteDepartment(departmentId: String, token: String)
    fun changeDepartmentName(changeNameDepartmentDTO: ChangeNameDepartmentDTO, token: String)
}

@Service
class DepartmentServiceImpl(
    private val departmentRepository: DepartmentRepository,
    private val restTemplate: RestTemplate,
    private val authenticationBaseUrl: String
) : DepartmentService {
    override fun createDepartment(departmentCreateDTO: DepartmentCreateDTO) {
        if(departmentRepository.findByNameAndCompanyToken(departmentCreateDTO.name, departmentCreateDTO.companyToken) != null) {
            throw AlreadyHasNameException("That department is created")
        }

        val dep = Department(
            departmentCreateDTO.name,
            departmentCreateDTO.users,
            departmentCreateDTO.companyToken
        )
        departmentRepository.save(dep)
    }

    override fun getAllDepartmentsForCompany(companyToken: String): List<Department> {
        return departmentRepository.findAllByCompanyToken(companyToken) ?: emptyList()
    }

    override fun addUsersToDepartment(addUserToDepartmentDTO: AddUserToDepartmentDTO) {
        val department = departmentRepository.findById(addUserToDepartmentDTO.departmentId) ?: throw NotFoundException()
        addUserToDepartmentDTO.users?.map {
                dtoUser ->
            department.users.map { departmentUser ->
                if(StringUtils.equals(departmentUser.id, dtoUser.id)){
                    department.users.remove(departmentUser)
                }
            }
            department.users.add(dtoUser)
        }
        departmentRepository.save(department)
    }

    override fun deleteUserFromDepartment(deleteUserFromDepartmentDTO: DeleteUserFromDepartmentDTO): Boolean {
        val department = departmentRepository.findById(deleteUserFromDepartmentDTO.departmentId ?: "") ?: return false
        department.users.removeIf {
            user->
            StringUtils.equals(deleteUserFromDepartmentDTO.userId, user.id)
        }
        departmentRepository.save(department)
        return true
    }

    override fun deleteDepartment(departmentId: String, token: String) {
        val ids: ArrayList<String> = ArrayList()
        departmentRepository.findById(departmentId)?.users?.map {
            ids.add(it.id)
        }

        val deleteDepartmentDTO = DeleteDepartmentDTO(
            ids
        )

        val header = HttpHeaders()
        header.set("Authorization", "Bearer $token");
        val requestEntity: HttpEntity<DeleteDepartmentDTO> = HttpEntity(deleteDepartmentDTO, header)

        try {
            val entity = restTemplate.exchange("$authenticationBaseUrl/user/delete-department-from-users", HttpMethod.POST, requestEntity, String::class.java)
            if(entity.body.toBoolean())
                departmentRepository.deleteById(departmentId)
            else
                throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.stackTrace.toString())
        }
    }

    override fun changeDepartmentName(changeNameDepartmentDTO: ChangeNameDepartmentDTO, token: String) {
        val department = departmentRepository.findById(changeNameDepartmentDTO.departmentId) ?: throw NotFoundException()
        department.name = changeNameDepartmentDTO.newName

        val header = HttpHeaders()
        header.set("Authorization", "Bearer $token");
        val requestEntity: HttpEntity<ChangeNameDepartmentDTO> = HttpEntity(changeNameDepartmentDTO, header)

        try {
            val entity = restTemplate.exchange("$authenticationBaseUrl/user/change-department-name", HttpMethod.POST, requestEntity, String::class.java)
            if(entity.body.toBoolean())
                departmentRepository.save(department)
            else
                throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.stackTrace.toString())
        }
    }
}