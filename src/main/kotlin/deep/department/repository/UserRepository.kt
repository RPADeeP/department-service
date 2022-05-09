package deep.department.repository

import deep.department.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, Int> {
    fun findByCode(code: String) : User
}