package com.ensar.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.Comment;
import com.ensar.entity.Testcase;
import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Release;
import com.ensar.entity.User;
import com.ensar.entity.Testcase;
import com.ensar.repository.CommentRepository;
import com.ensar.repository.TestcaseRepository;
import com.ensar.repository.FeatureRepository;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ReleaseRepository;
import com.ensar.repository.UserRepository;
import com.ensar.repository.TestcaseRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.request.dto.CreateUpdateTestcaseDto;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class TestcaseService {

    
	@Autowired
	private ModelMapper modelMapper;
	

	@Autowired
	private TestcaseRepository testcaseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
  

    @Autowired
    public TestcaseService(TestcaseRepository testcaseRepository) {

        this.testcaseRepository = testcaseRepository;
       
    }


    public Testcase getTestcaseById(Long id) {
        Optional<Testcase> testcaseOptional = testcaseRepository.findById(id);

        if (!testcaseOptional.isPresent())
            throw new RuntimeException("testcase Optional with " + id + " not found.");
        Testcase testcase = testcaseOptional.get();
        
        return testcase;
    }

    

    public List<Testcase> getAllTestcases() {
        
    	List<Testcase> testcases = testcaseRepository.findAll();
    	
    	    	
        return testcases;
    }
    
    public Map<String, Object> getAllTestcases(Pageable pageable) {
        
    	Page<Testcase> testcases = testcaseRepository.findAll(pageable);
    	
        	
    	Map<String, Object> response = new HashMap<>();

		response.put("testcases", testcases.getContent());
		response.put("currentPage", testcases.getNumber());
		response.put("totalItems", testcases.getTotalElements());
		response.put("totalPages", (testcases.getTotalElements() / pageable.getPageSize()+1));

		return response;
    	        
    }

    public Testcase createOrUpdateTestcase(Optional<Long> testcaseId, CreateUpdateTestcaseDto createUpdateTestcaseDto) {
    	Testcase testcase;
        if (testcaseId.isPresent()) {
        	testcase = testcaseRepository.getById(testcaseId.get());
            if (testcase == null)
                throw new RuntimeException("Testcase with id " + testcaseId.get() + " not found");
            //testcase = modelMapper.map(createUpdateTestcaseDto, Testcase.class);
            
           	testcase.setId(testcaseId.get());
           
        } else {
        	testcase = new Testcase();
            if (testcaseRepository.existsByTestId(createUpdateTestcaseDto.getTestId()))
                throw new RuntimeException("Testcase with name " + createUpdateTestcaseDto.getTestId() + " already exists.");
            ////testcase = modelMapper.map(createUpdateTestcaseDto, Testcase.class);
           
          
        }

        testcase.setName(createUpdateTestcaseDto.getName());
        testcase.setTestId(createUpdateTestcaseDto.getTestId());
        testcase.setDescription(createUpdateTestcaseDto.getDescription());
        testcase.setActualResults(createUpdateTestcaseDto.getActualResults());
        testcase.setExpectedResults(createUpdateTestcaseDto.getExpectedResults());
        testcase.setStatus(createUpdateTestcaseDto.getStatus());
       
        
        
        if (StringUtils.hasText(createUpdateTestcaseDto.getCreatedBy())) {
        
        	User user = userRepository.findByEmail(createUpdateTestcaseDto.getCreatedBy());
            if (user == null)
                throw new RuntimeException("User with id " + createUpdateTestcaseDto.getCreatedBy() + " not found");
            
            testcase.setUser(user);
           
        }
        
       
        
        testcase = testcaseRepository.save(testcase);
        
		
        

        return testcase;
    }


	public Long deleteTestcaseById(Long id) {
		testcaseRepository.deleteById(id);
		return id;
	}

   


}
