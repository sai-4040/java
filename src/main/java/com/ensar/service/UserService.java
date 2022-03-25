package com.ensar.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.User;
import com.ensar.entity.UserPasswordResetRequest;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.UserPasswordResetRequestRepository;
import com.ensar.repository.UserRepository;
import com.ensar.request.dto.CreateUpdateUserDto;
import com.ensar.security.EnsarUserDetails;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private OrganizationService organizationService;

    private OrganizationRepository organizationRepository;

    private UserPasswordResetRequestRepository userPasswordResetRequestRepository;

    private BCryptPasswordEncoder bCryptEncoder;

    private QuickSightURLGenerator quickSightURLGenerator;

    private EmailSender emailSender;

    @Value("${app.url.prefix:}")
    private String serverUrlPrefix;


    @Autowired
    public UserService(UserRepository userRepository,
                       OrganizationRepository organizationRepository,
                       OrganizationService organizationService,
                       UserPasswordResetRequestRepository userPasswordResetRequestRepository,
                       BCryptPasswordEncoder bCryptEncoder, ForecastUrlRepository forecastUrlRepository,
                       QuickSightURLGenerator quickSightURLGenerator,
                       EmailSender emailSender) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.organizationService = organizationService;
        this.userPasswordResetRequestRepository = userPasswordResetRequestRepository;
        this.bCryptEncoder = bCryptEncoder;
        this.quickSightURLGenerator = quickSightURLGenerator;
        this.emailSender = emailSender;
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent())
            throw new RuntimeException("User with " + id + " not found.");

        return userOptional.get();
    }

    public List<User> getAllAccessibleUsers() {
        User user = getLoggedInUser();
        if (user.getRole().equals(User.Role.ROLE_SUPER_ADMIN))
            return userRepository.findAll();

        if (user.getRole().equals(User.Role.ROLE_ADMIN))
            return userRepository.findByOrganization(user.getOrganization());

        return List.of();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updatePassword(String token, String newPassword) {
        UserPasswordResetRequest userPasswordResetRequest = userPasswordResetRequestRepository.getById(token);
        User user = userRepository.getById(userPasswordResetRequest.getUserId());
        user.setPassword(bCryptEncoder.encode(newPassword));
        userRepository.save(user);
        String subject = "Agile Account password successfully changed.";
        String message = "You've successfully updated your password for your Agile Account. Please login to your account" +
                " using link : " + serverUrlPrefix;
        emailSender.sendSimpleMessage(user.getEmail(), subject, message);

        return user;
    }

    public UserPasswordResetRequest createPasswordResetRequest(String email, boolean newUser) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        UserPasswordResetRequest userPasswordResetRequest = new UserPasswordResetRequest();
        userPasswordResetRequest.setUserId(user.getId());
        userPasswordResetRequest.setExpireDateTime(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        userPasswordResetRequestRepository.save(userPasswordResetRequest);


        String subject = newUser ? "Welcome to Vizen Analytics" :"Reset your Vizen Analytics password";
        String message = "Update your Vizen Analytics account password using link: " + generateResetPasswordUrl(userPasswordResetRequest.getId());
        log.info(message);
        emailSender.sendSimpleMessage(user.getEmail(), subject, message);

        return userPasswordResetRequest;

    }

    public UserPasswordResetRequest getPasswordResetRequest(String id) {
        return userPasswordResetRequestRepository.getById(id);
    }

    public List<ForecastDashBoard> getForecastUrls(String email) {
        User user = userRepository.findByEmail(email);
        Optional<ForecastDashBoard> forecastDashBoard = organizationService.getLatestDashBoardByOrg(user.getOrganization().getId());

        return forecastDashBoard.isPresent() ? List.of(refreshUrlIfNeeded(forecastDashBoard.get())) : Collections.emptyList();
    }

    private ForecastDashBoard refreshUrlIfNeeded(ForecastDashBoard forecastDashBoard) {
        //if(StringUtils.hasText(forecastDashBoard.getDashBoardId())) {
            log.info("#### Generating URL for dashboard Id:" + forecastDashBoard.getDashBoardId());
            String url = quickSightURLGenerator.getQuicksightEmbedUrl(forecastDashBoard.getDashBoardId());
            forecastDashBoard.setUrl(url);
        //}
        return forecastDashBoard;
    }

    @Override
    public EnsarUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username.");
        }
        return new EnsarUserDetails(user);
    }

    public User createOrUpdateUser(Optional<Long> userId, CreateUpdateUserDto createUpdateUserDto) {
        Optional<User> userOptional;
        User user;
        if (userId.isPresent()) {
            userOptional = userRepository.findById(userId.get());
            if (!userOptional.isPresent())
                throw new RuntimeException("User with id " + userId.get() + " not found");
            user = userOptional.get();
        } else {
            if (userRepository.existsByEmail(createUpdateUserDto.getEmail())) {
                throw new RuntimeException("User with email " + createUpdateUserDto.getEmail() + " already exists.");
            }
            user = new User();
        }
        User currentUser = getLoggedInUser();

        Optional<Organization> orgOptional = organizationRepository.findById(createUpdateUserDto.getOrganizationId());
        if (!orgOptional.isPresent() ||
                (User.Role.ROLE_ADMIN.equals(currentUser.getRole()) &&
                        !currentUser.getOrganization().getId().equals(createUpdateUserDto.getOrganizationId())))
            throw new RuntimeException("Invalid organization.");

        user.setEmail(createUpdateUserDto.getEmail());
        user.setFirstName(createUpdateUserDto.getFirstName());
        user.setLastName(createUpdateUserDto.getLastName());
        user.setOrganization(orgOptional.get());
        user.setRole(createUpdateUserDto.getRole());
        user = userRepository.save(user);

        createPasswordResetRequest(user.getEmail(), true);
        return user;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserByEmail(authentication.getName());
    }

    public void enableOrDisableUsers(List<Long> userIdList, final boolean disabled) {
        userIdList.forEach(id-> {userRepository.getById(id).setDisabled(disabled);});
    }

    private String generateResetPasswordUrl(Long token) {
        return serverUrlPrefix + "/reset-password?token=" + token;
    }

}
