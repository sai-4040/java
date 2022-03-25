package com.ensar.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.Comment;
import com.ensar.entity.Epic;
import com.ensar.entity.Story;
import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Release;
import com.ensar.entity.User;
import com.ensar.entity.Story;
import com.ensar.repository.CommentRepository;
import com.ensar.repository.EpicRepository;
import com.ensar.repository.StoryRepository;
import com.ensar.repository.FeatureRepository;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ReleaseRepository;
import com.ensar.repository.UserRepository;
import com.ensar.repository.StoryRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.response.dto.StoryResponse;
import com.ensar.request.dto.CreateUpdateStoryDto;

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
public class StoryService {

    @Autowired
    private ModelMapper modelMapper;

    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository) {

        this.storyRepository = storyRepository;

    }

    public Story getStoryById(Long id) {
        Optional<Story> storyOptional = storyRepository.findById(id);

        if (!storyOptional.isPresent())
            throw new RuntimeException("story Optional with " + id + " not found.");
        Story story = storyOptional.get();

        return story;
    }

    public List<StoryResponse> getAllStorys() {

        List<Story> storys = storyRepository.findAll();

        List<StoryResponse> respList = storys.stream()
                .map(vehiclevideolibrary -> modelMapper.map(storys, StoryResponse.class)).collect(Collectors.toList());

        return respList;
    }

    public Map<String, Object> getAllStorys(Pageable pageable) {

        Page<Story> storys = storyRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();

        response.put("stories", storys.getContent());
        response.put("currentPage", storys.getNumber());
        response.put("totalItems", storys.getTotalElements());
        response.put("totalPages", (storys.getTotalElements() / pageable.getPageSize() + 1));

        return response;

    }

    public Story createOrUpdateStory(Optional<Long> storyId, CreateUpdateStoryDto createUpdateStoryDto) {
        Story story;
        if (storyId.isPresent()) {
            story = storyRepository.findById(storyId.get()).get();
            if (story == null)
                throw new RuntimeException("Story with id " + storyId.get() + " not found");
            // if (StringUtils.hasText(createUpdateStoryDto.getName())) {
            // story.setName(createUpdateStoryDto.getName());
            // }
            story = modelMapper.map(createUpdateStoryDto, Story.class);
            story.setId(storyId.get());
        } else {
            story = new Story();
            if (storyRepository.existsByName(createUpdateStoryDto.getName()))
                throw new RuntimeException("Story with name " + createUpdateStoryDto.getName() + " already exists.");
            // story.setName(createUpdateStoryDto.getName());
            // story.setDescription(createUpdateStoryDto.getDescription());
            story = modelMapper.map(createUpdateStoryDto, Story.class);
        }

        if (StringUtils.hasText(createUpdateStoryDto.getCreatedBy())) {

            User user = userRepository.findByEmail(createUpdateStoryDto.getCreatedBy());
            if (user == null)
                throw new RuntimeException("User with id " + createUpdateStoryDto.getCreatedBy() + " not found");

            story.setUser(user);

        }

        if (StringUtils.hasText(createUpdateStoryDto.getFeature())) {

            Feature feature = featureRepository.findByName(createUpdateStoryDto.getFeature());

            if (feature == null)
                throw new RuntimeException("Feature with id " + createUpdateStoryDto.getCreatedBy() + " not found");

            story.setFeature(feature);

        }

        if (StringUtils.hasText(createUpdateStoryDto.getRelease())) {

            Release release = releaseRepository.findByName(createUpdateStoryDto.getRelease());

            if (release == null)
                throw new RuntimeException("Release with id " + createUpdateStoryDto.getRelease() + " not found");

            story.setRelease(release);

        }
        
        if (createUpdateStoryDto.getEpic() != null) {

            Epic epic = epicRepository.getById(createUpdateStoryDto.getEpic());
            if (epic == null)
                throw new RuntimeException("epic with id " + createUpdateStoryDto.getEpic() + " not found");

            story.setEpic(epic);

        }

        story = storyRepository.save(story);

        /*if (StringUtils.hasText(createUpdateStoryDto.getComment())) {

            Comment comment = new Comment();
            comment.setName(createUpdateStoryDto.getComment());
            comment.setStory(story);
            comment = commentRepository.save(comment);
            if (story.getComments() != null) {
                story.getComments().add(comment);
            } else {
                List<Comment> comments = new ArrayList<Comment>();
                comments.add(comment);
                story.setComments(comments);
            }

        }*/

        return story;
    }

    public Long deleteStoryById(Long id) {
        storyRepository.deleteById(id);
        return id;
    }

}
