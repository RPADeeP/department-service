package deep.department.service

import deep.department.dto.CompanyCreateDTO
import deep.department.model.Company
import deep.department.repository.CompanyRepository
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

interface CompanyService {
    fun createCompany(companyCreateDTO: CompanyCreateDTO) : String
}

@Service
class CompanyServiceImpl(
    private val companyRepository: CompanyRepository
) : CompanyService {
    override fun createCompany(companyCreateDTO: CompanyCreateDTO) : String {
        val company = Company(
            companyCreateDTO.name,
            companyCreateDTO.departments
        )
        try {
        companyRepository.save(company)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.stackTrace.toString())
        }
        return company.id
    }
}