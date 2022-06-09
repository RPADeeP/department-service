package deep.department.repository

import deep.department.model.Department
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : MongoRepository<Department, Int> {
    fun findAllByCompanyToken(companyToken: String) : List<Department>?
    fun findByNameAndCompanyToken(name: String, companyToken: String) : Department?
    fun findById(id: String): Department?
    fun deleteById(id: String)
}