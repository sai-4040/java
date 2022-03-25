package com.ensar.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.Comment;
import com.ensar.entity.Ticket;
import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Release;
import com.ensar.entity.User;
import com.ensar.entity.Ticket;
import com.ensar.repository.CommentRepository;
import com.ensar.repository.TicketRepository;
import com.ensar.repository.FeatureRepository;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ReleaseRepository;
import com.ensar.repository.UserRepository;
import com.ensar.repository.TicketRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.request.dto.CreateUpdateTicketDto;

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
public class TicketService {

    
	@Autowired
	private ModelMapper modelMapper;
	

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
  

    @Autowired
    public TicketService(TicketRepository ticketRepository) {

        this.ticketRepository = ticketRepository;
       
    }


    public Ticket getTicketById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (!ticketOptional.isPresent())
            throw new RuntimeException("ticket Optional with " + id + " not found.");
        Ticket ticket = ticketOptional.get();
        
        return ticket;
    }

    

    public List<Ticket> getAllTickets() {
        
    	List<Ticket> tickets = ticketRepository.findAll();
    	
    	    	
        return tickets;
    }
    
    public Map<String, Object> getAllTickets(Pageable pageable) {
        
    	Page<Ticket> tickets = ticketRepository.findAll(pageable);
    	
        	
    	Map<String, Object> response = new HashMap<>();

		response.put("tickets", tickets.getContent());
		response.put("currentPage", tickets.getNumber());
		response.put("totalItems", tickets.getTotalElements());
		response.put("totalPages", (tickets.getTotalElements() / pageable.getPageSize()+1));

		return response;
    	        
    }

    public Ticket createOrUpdateTicket(Optional<Long> ticketId, CreateUpdateTicketDto createUpdateTicketDto) {
    	Ticket ticket;
        if (ticketId.isPresent()) {
        	ticket = ticketRepository.getById(ticketId.get());
            if (ticket == null)
                throw new RuntimeException("Ticket with id " + ticketId.get() + " not found");
            
            ticket.setName(createUpdateTicketDto.getName());
            ticket.setStatus(createUpdateTicketDto.getStatus());
            ticket.setType(createUpdateTicketDto.getType());
            ticket.setPriority(createUpdateTicketDto.getPriority());
            
        } else {
        	ticket = new Ticket();
           
            ////ticket = modelMapper.map(createUpdateTicketDto, Ticket.class);
            ticket.setName(createUpdateTicketDto.getName());
            ticket.setStatus(createUpdateTicketDto.getStatus());
            ticket.setType(createUpdateTicketDto.getType());
            ticket.setPriority(createUpdateTicketDto.getPriority());
          
        }

       
        
        
        if (StringUtils.hasText(createUpdateTicketDto.getCreatedBy())) {
        
        	User user = userRepository.findByEmail(createUpdateTicketDto.getCreatedBy());
            if (user == null)
                throw new RuntimeException("User with id " + createUpdateTicketDto.getCreatedBy() + " not found");
            
            ticket.setUser(user);
           
        }
        
       
        
        ticket = ticketRepository.save(ticket);
        
		
        

        return ticket;
    }


	public Long deleteTicketById(Long id) {
		ticketRepository.deleteById(id);
		return id;
	}

   


}
