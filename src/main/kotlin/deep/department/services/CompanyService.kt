package deep.department.services

import deep.department.component.dto.CompanyCreateDTO
import deep.department.model.Company
import deep.department.repository.CompanyRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

interface CompanyService {
    fun createCompany(companyCreateDTO: CompanyCreateDTO) : ObjectId
}

@Service
class CompanyServiceImpl(
    val companyRepository: CompanyRepository
) : CompanyService {
    override fun createCompany(companyCreateDTO: CompanyCreateDTO) : ObjectId {
        val company = Company(
            companyCreateDTO.name,
            companyCreateDTO.departments
        )
        companyRepository.save(company)
        return company.id
    }
}