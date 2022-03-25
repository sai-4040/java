package com.ensar.service;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.Comment;
import com.ensar.entity.Epic;
import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Release;
import com.ensar.entity.User;
import com.ensar.entity.Epic;
import com.ensar.repository.CommentRepository;
import com.ensar.repository.EpicRepository;
import com.ensar.repository.FeatureRepository;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ReleaseRepository;
import com.ensar.repository.UserRepository;
import com.ensar.repository.EpicRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.response.dto.EpicResponse;
import com.ensar.request.dto.CreateUpdateEpicDto;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.Caching;

@Service
@Transactional
////@CacheConfig(cacheNames = "epicCache")
public class EpicService {

    
	@Autowired
	private ModelMapper modelMapper;
	
	private EpicRepository epicRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private FeatureRepository featureRepository;

	@Autowired
    private ReleaseRepository releaseRepository;
	
	@Autowired
    private CommentRepository commentRepository;
  

    @Autowired
    public EpicService(EpicRepository epicRepository) {

        this.epicRepository = epicRepository;
       
    }


	////@Cacheable(cacheNames = "epic", key = "#id", unless = "#result == null")
    public Epic getEpicById(Long id) {
        Optional<Epic> epicOptional = epicRepository.findById(id);

        if (!epicOptional.isPresent())
            throw new RuntimeException("epic Optional with " + id + " not found.");
        Epic epic = epicOptional.get();
        
        return epic;
    }

    

	
    public List<EpicResponse> getAllEpics() {
        
    	List<Epic> epics = epicRepository.findAll();
    	
    	List<EpicResponse> respList = epics.stream()
				.map(vehiclevideolibrary -> modelMapper.map(epics, EpicResponse.class))
				.collect(Collectors.toList());
    	
        return respList;
    }
    
    ////@Cacheable(cacheNames = "epics")
    public Map<String, Object> getAllEpics(Pageable pageable) {
        
    	Page<Epic> epics = epicRepository.findAll(pageable);
    	
    	
    	
    	Map<String, Object> response = new HashMap<>();

		response.put("epics", epics.getContent());
		response.put("currentPage", epics.getNumber());
		response.put("totalItems", epics.getTotalElements());
		response.put("totalPages", (epics.getTotalElements() / pageable.getPageSize()+1));

		return response;
    	        
    }

	////@CacheEvict(cacheNames = "epics", allEntries = true)
    public Epic createOrUpdateEpic(Optional<Long> epicId, CreateUpdateEpicDto createUpdateEpicDto) {
    	Epic epic;
        if (epicId.isPresent()) {
        	epic= epicRepository.findById(epicId.get()).get();
            if (epic == null)
                throw new RuntimeException("Epic with id " + epicId.get() + " not found");
            
            epic = modelMapper.map(createUpdateEpicDto, Epic.class);
            epic.setId(epicId.get());
        } else {
        	epic = new Epic();
            //if (epicRepository.existsByName(createUpdateEpicDto.getName()))
            //    throw new RuntimeException("Epic with name " + createUpdateEpicDto.getName() + " already exists.");
            
            epic = modelMapper.map(createUpdateEpicDto, Epic.class);
        }

       
        
        
        if (StringUtils.hasText(createUpdateEpicDto.getCreatedBy())) {
        
        	User user = userRepository.findByEmail(createUpdateEpicDto.getCreatedBy());
            if (user == null)
                throw new RuntimeException("User with id " + createUpdateEpicDto.getCreatedBy() + " not found");
            
            epic.setUser(user);
           
        }
        
        if (StringUtils.hasText(createUpdateEpicDto.getFeature())) {
            
        	Feature feature = featureRepository.findByName(createUpdateEpicDto.getFeature());
            
        	if (feature == null)
                throw new RuntimeException("Feature with id " + createUpdateEpicDto.getCreatedBy() + " not found");
            
            epic.setFeature(feature);
           
        }
        
        if (StringUtils.hasText(createUpdateEpicDto.getRelease())) {
            
        	Release release = releaseRepository.findByName(createUpdateEpicDto.getRelease());
            
        	if (release == null)
                throw new RuntimeException("Release with id " + createUpdateEpicDto.getRelease() + " not found");
            
            epic.setRelease(release);
           
        }
        
        epic = epicRepository.save(epic);
        
		/*if (StringUtils.hasText(createUpdateEpicDto.getComment())) {
		            
			Comment comment = new Comment();
			comment.setName(createUpdateEpicDto.getComment());
			comment.setEpic(epic);
			comment = commentRepository.save(comment);
			if(epic.getComments() != null )
			{
				epic.getComments().add(comment);
			}
			else
			{
				List<Comment> comments =  new ArrayList<Comment>();
				comments.add(comment);
				epic.setComments(comments);
			}
		           
		 }*/
        

        return epic;
    }

	////@Caching(evict = { @CacheEvict(cacheNames = "epic", key = "#id"),
	////		@CacheEvict(cacheNames = "epics", allEntries = true) })
	public Long deleteEpicById(Long id) {
		epicRepository.deleteById(id);
		return id;
	}

   


}
