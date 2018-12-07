package com.ncr.project.pulsecheck.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ncr.project.pulsecheck.domain.User;
import com.ncr.project.pulsecheck.domain.UserExt;
import com.ncr.project.pulsecheck.repository.UserRepository;
import com.ncr.project.pulsecheck.security.SecurityUtils;
import com.ncr.project.pulsecheck.service.MailService;
import com.ncr.project.pulsecheck.service.QuestionnaireAnswerService;
import com.ncr.project.pulsecheck.service.QuestionnaireService;
import com.ncr.project.pulsecheck.service.UserExtService;
import com.ncr.project.pulsecheck.service.UserService;
import com.ncr.project.pulsecheck.service.dto.UserDTO;
import com.ncr.project.pulsecheck.service.dto.UserExtDTO;
import com.ncr.project.pulsecheck.service.dto.UserExtWRelationsDTO;
import com.ncr.project.pulsecheck.service.mapper.UserExtWRelationsMapper;
import com.ncr.project.pulsecheck.web.rest.errors.*;
import com.ncr.project.pulsecheck.web.rest.vm.KeyAndPasswordVM;
import com.ncr.project.pulsecheck.web.rest.vm.ManagedUserVM;
import com.ncr.project.pulsecheck.web.rest.vm.OrganizationAndEventsVM;
import com.ncr.project.pulsecheck.web.rest.vm.UserEventsVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.ncr.project.pulsecheck.service.dto.ParticipantDTO;
import com.ncr.project.pulsecheck.service.dto.PasswordChangeDTO;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireAnswerDTO;
import com.ncr.project.pulsecheck.service.dto.QuestionnaireDTO;

import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserExtService userExtService;

    private final QuestionnaireService questionnaireService;

    private final QuestionnaireAnswerService questionnaireAnswerService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService, UserExtService userExtService, UserExtWRelationsMapper userExtWRelationsMapper, QuestionnaireService questionnaireService, QuestionnaireAnswerService questionnaireAnswerService) {
        this.userExtService = userExtService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.questionnaireService = questionnaireService;
        this.questionnaireAnswerService = questionnaireAnswerService;
    }

    /**
     * POST /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @throws InvalidPasswordException  400 (Bad Request) if the password is
     *                                   incorrect/PUT
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already
     *                                   used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already
     *                                   used
     */
    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).ifPresent(u -> {throw new LoginAlreadyUsedException();});
        userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).ifPresent(u -> {throw new EmailAlreadyUsedException();});
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(userService::newUserDTO)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }
    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account/ext")
    @Timed
    public UserExtWRelationsDTO getAccountExt() {
        Optional<User> userWithAuthorities = userService.getUserWithAuthorities();
        if(!userWithAuthorities.isPresent()) throw new InternalServerErrorException("User could not be found");
        User user = userWithAuthorities.get();
        Optional<UserExtWRelationsDTO> userExt = userExtService
                .findOneByEmailWithRelationship(user.getEmail());

        return userExt
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    @GetMapping("/account/questionnaires")
    @Timed
    public List<QuestionnaireDTO> getAccountQuestionnaires() {
        Optional<User> userWithAuthorities = userService.getUserWithAuthorities();
        if(!userWithAuthorities.isPresent()) throw new InternalServerErrorException("User could not be found");
        User user = userWithAuthorities.get();
        Optional<UserExtWRelationsDTO> userExt = userExtService
                .findOneByEmailWithRelationship(user.getEmail());
        if (!userExt.isPresent())
            throw new InternalServerErrorException("User could not be found");
        
        Optional<List<QuestionnaireDTO>> ret = questionnaireService.findAllByUserExt(userExt.get().getId());
        return ret
            .orElseThrow(() -> new InternalServerErrorException("No questionnaire found"));
    }

    @GetMapping("/account/questionnaire/{questionnaireId}/answers")
    @Timed
    public List<QuestionnaireAnswerDTO> getAccountQuestionnaireAnswers(@PathVariable Long questionnaireId) {
        Optional<User> userWithAuthorities = userService.getUserWithAuthorities();
        if(!userWithAuthorities.isPresent()) throw new InternalServerErrorException("User could not be found");
        User user = userWithAuthorities.get();
        Optional<UserExtWRelationsDTO> userExt = userExtService
                .findOneByEmailWithRelationship(user.getEmail());
        if (!userExt.isPresent())
                throw new InternalServerErrorException("User could not be found");
        Optional<List<QuestionnaireAnswerDTO>> ret = questionnaireAnswerService.findAllByQuestionnaire(questionnaireId);
        return ret
            .orElseThrow(() -> new InternalServerErrorException("No questionnaire found"));
        // return userExt
        //     .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }
    
    /**
     * GET  /account/events : get the events associated to current logged user. Events are grouped into participant and lead group.
     *
     * @return events for current logged user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account/events")
    @Timed
    public UserEventsVM getAccountEvents() {
        // return userService.getUserWithAuthorities()
        //     .map(userService::newUserDTO)
        //     .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        Optional<User> loggedUser = userService.getUserWithAuthorities();
        Optional<UserEventsVM> ret = loggedUser.map(u -> u.getEmail()).flatMap(userExtService::findUserEventsVMByEmail);

        return ret.get();
    }


       /**
     * GET  /account/organizations : get the organizations manageable from current logged user.
     *
     * @return organizations for current logged user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account/organizations")
    @Timed
    public List<OrganizationAndEventsVM> getAccountOrganizations() {
        // return userService.getUserWithAuthorities()
        //     .map(userService::newUserDTO)
        //     .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
        Optional<User> loggedUser = userService.getUserWithAuthorities();
        Optional<List<OrganizationAndEventsVM>> ret = loggedUser.map(u -> u.getEmail()).flatMap(userExtService::findUserOrganizationsVMByEmail);

        return ret.get();
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account")
    @Timed
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl(),userDTO.getJobRole());
   }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
   }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
     * POST   /account/reset-password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
