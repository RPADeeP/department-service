package deep.department.controller

import deep.department.dto.CompanyCreateDTO
import deep.department.service.CompanyService
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/company")
@Secured
class CompanyController(
    val companyService: CompanyService
    ) {

    @PostMapping(path=["/create"])
    fun create(@RequestBody companyCreateDTO: CompanyCreateDTO) : String {
        return companyService.createCompany(companyCreateDTO)
    }
}