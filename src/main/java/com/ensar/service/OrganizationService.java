package com.ensar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    private ForecastUrlRepository forecastUrlRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository,
                               ForecastUrlRepository forecastUrlRepository) {

        this.organizationRepository = organizationRepository;
        this.forecastUrlRepository = forecastUrlRepository;
    }


    public Organization getOrganizationById(Long id) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);

        if (!organizationOptional.isPresent())
            throw new RuntimeException("Organization with " + id + " not found.");
        Organization organization = organizationOptional.get();
        populateDashBoardId(organization);
        return organization;
    }

    private void populateDashBoardId(Organization organization) {
        Optional<ForecastDashBoard> latestDashBoardIdByOrg = getLatestDashBoardByOrg(organization.getId());
        if (latestDashBoardIdByOrg.isPresent())
            organization.setDashBoardId(latestDashBoardIdByOrg.get().getDashBoardId());
    }

    public Optional<ForecastDashBoard> getLatestDashBoardByOrg(Long orgId) {
        List<ForecastDashBoard> dashBoardList = forecastUrlRepository.findByOrganizationIdOrderByCreatedDateTimeDesc(orgId);
        return dashBoardList.isEmpty() ? Optional.empty() : Optional.of(dashBoardList.get(0));
    }

    public List<Organization> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        organizations.forEach(o -> populateDashBoardId(o));
        return organizations;
    }

    public Organization createOrUpdateOrganization(Optional<Long> orgId, CreateUpdateOrgDto createUpdateOrgDto) {
        Organization organization;
        if (orgId.isPresent()) {
            organization = organizationRepository.getById(orgId.get());
            if (organization == null)
                throw new RuntimeException("User with id " + orgId.get() + " not found");
        } else {
            organization = new Organization();
            if (organizationRepository.existsByName(createUpdateOrgDto.getName()))
                throw new RuntimeException("Organization with name " + createUpdateOrgDto.getName() + " already exists.");
        }

        organization.setName(createUpdateOrgDto.getName());
        organization.setDescription(createUpdateOrgDto.getDescription());
        organization.setDomain(createUpdateOrgDto.getDomain());

        organization = organizationRepository.save(organization);

       // if (StringUtils.hasText(createUpdateOrgDto.getDashBoardId())) {
            Optional<ForecastDashBoard> forecastDashBoardOptional = getLatestDashBoardByOrg(createUpdateOrgDto.getDashBoardId());
            ForecastDashBoard forecastDashBoard;
            if (forecastDashBoardOptional.isPresent()) {
                forecastDashBoard = forecastDashBoardOptional.get();
            } else {
                forecastDashBoard = new ForecastDashBoard();
                forecastDashBoard.setOrganizationId(organization.getId());
            }
            forecastDashBoard.setDashBoardId(createUpdateOrgDto.getDashBoardId());
            forecastUrlRepository.save(forecastDashBoard);
        //}
        organization.setDashBoardId(createUpdateOrgDto.getDashBoardId());
        return organization;
    }

    public void enableOrDisableOrgs(List<Long> orgIdList, final boolean disabled) {
        orgIdList.forEach(id -> {
            organizationRepository.getById(id).setDisabled(disabled);
        });
    }


}
