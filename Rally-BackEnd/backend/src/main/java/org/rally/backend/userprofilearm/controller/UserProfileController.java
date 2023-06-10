package org.rally.backend.userprofilearm.controller;

import org.rally.backend.eventsarm.models.Event;
import org.rally.backend.eventsarm.repository.EventRepository;
import org.rally.backend.forumarm.models.ForumPosts;
import org.rally.backend.forumarm.models.Replies;
import org.rally.backend.forumarm.repository.ForumPostRepository;
import org.rally.backend.forumarm.repository.RepliesRepository;
import org.rally.backend.servicesarm.model.response.Service;
import org.rally.backend.servicesarm.repository.ServiceRepository;
import org.rally.backend.springsecurity.models.BadJWT;
import org.rally.backend.springsecurity.repository.JWTBlockListRepository;
import org.rally.backend.springsecurity.security.jwt.JWTGenerator;
import org.rally.backend.userprofilearm.exception.MinimumCharacterException;
import org.rally.backend.userprofilearm.model.*;
import org.rally.backend.userprofilearm.model.dto.DirectMessageDTO;
import org.rally.backend.userprofilearm.model.dto.HidePostDTO;
import org.rally.backend.userprofilearm.model.dto.UserInfoDTO;
import org.rally.backend.userprofilearm.model.response.ResponseMessage;
import org.rally.backend.userprofilearm.model.UserPostHistory;
import org.rally.backend.userprofilearm.repository.*;
import org.rally.backend.userprofilearm.utility.ImageUtility;
import org.rally.backend.userprofilearm.utility.UserProfileControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequestMapping(value = "/user")
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;
    private final RoleRepository roleRepository;
    private final DirectMessageRepository directMessageRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final ForumPostRepository forumPostRepository;
    private final RepliesRepository repliesRepository;
    private final HiddenPostRepository hiddenPostRepository;
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final JWTBlockListRepository jwtBlockListRepository;
    private final JWTGenerator jwtGenerator;


    @Autowired
    public UserProfileController(UserRepository userRepository, RoleRepository roleRepository,
                                 UserInformationRepository userInformationRepository,
                                 DirectMessageRepository directMessageRepository, ProfilePictureRepository profilePictureRepository,
                                 ForumPostRepository forumPostRepository, RepliesRepository repliesRepository,
                                 HiddenPostRepository hiddenPostRepository, ServiceRepository serviceRepository,
                                 EventRepository eventRepository, JWTGenerator jwtGenerator,
                                 JWTBlockListRepository jwtBlockListRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userInformationRepository = userInformationRepository;
        this.directMessageRepository = directMessageRepository;
        this.profilePictureRepository = profilePictureRepository;
        this.forumPostRepository = forumPostRepository;
        this.repliesRepository = repliesRepository;
        this.hiddenPostRepository = hiddenPostRepository;
        this.serviceRepository = serviceRepository;
        this.eventRepository = eventRepository;
        this.jwtGenerator = jwtGenerator;
        this.jwtBlockListRepository = jwtBlockListRepository;
    }

    /** GET REQUEST **/
    /** GET REQUEST **/
    /** GET REQUEST **/


    /** Returns a list of all users for search component **/
    @GetMapping("/search")
    public SearchUserThumbNail displayAllUsers() {
        SearchUserThumbNail thumbNail = new SearchUserThumbNail();
        List<UserEntity> userNames = new ArrayList<>();
        List<ProfilePicture> profilePictures = new ArrayList<>();

        /** Send user Entities with their profile pictures to the front **/
        for (UserEntity user : this.userRepository.findAll()) {
            if (profilePictureRepository.findByUserName(user.getUserName()).isPresent()) {

                userNames.add(user);
                ProfilePicture pic = profilePictureRepository.findByUserName(user.getUserName()).get();

                profilePictures.add(ProfilePicture.builder()
                        .id(pic.getId())
                        .userName(user.getUserName())
                        .type(pic.getType())
                        .image(ImageUtility.decompressImage(pic.getImage())).build());

            } else {
                userNames.add(user);
            }
        }

        thumbNail.setProfilePics(profilePictures);
        thumbNail.setUserNames(userNames);

        return thumbNail;
    }

    /** Returns information for a user being viewed **/
    @GetMapping("/getViewUserBundleInformation/{userName}")
    public ResponseEntity<?> getViewUserInformation(@PathVariable String userName) {

        Optional<UserEntity> areYouThere = Optional.ofNullable(userRepository.findByUserName(userName));
        if (areYouThere.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("404"), HttpStatus.OK);
        }

        /** Find user and set objects to send to the front **/
        UserEntity targetUser = userRepository.findByUserName(userName);
        UserBundle viewUserProfileBundle = new UserBundle();

        /** Find viewing users information **/
        viewUserProfileBundle.setViewUser(userRepository.findByUserName(userName));
        viewUserProfileBundle.setViewUserInformation(userInformationRepository.findByUserName(targetUser.getUserName()));
        viewUserProfileBundle.setViewUserDmHistory(UserProfileControllerService.activeUserDirectMessageHistory(targetUser.getId()));
        viewUserProfileBundle.setUpdatedPostHistoryViewUser(UserProfileControllerService.sortUpdatedPostHistoryViewUser(targetUser.getId()));


        return new ResponseEntity<>(viewUserProfileBundle, HttpStatus.OK);

    }

    /** Returns a list of all information for the logged in users account **/
    @GetMapping("/getMainUserBundleInformation/{userName}")
    public ResponseEntity<?> getMainUserBundle(@PathVariable String userName, @RequestHeader (name="authorization") String token) {

        Optional<BadJWT> test = Optional.ofNullable(jwtBlockListRepository.findByBadToken(token.substring(7, token.length())));

        if (!jwtGenerator.validateToken(token.substring(7, token.length())) || test.isPresent()) {
            ResponseMessage responseMessage = new ResponseMessage("Bad Token");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> areYouThere = Optional.ofNullable(userRepository.findByUserName(userName));
        if (areYouThere.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("404"), HttpStatus.OK);
        }

        /** Main user information and DM history **/
        UserEntity targetUser = userRepository.findByUserName(userName);
        Optional<UserInformation> targetInformation = userInformationRepository.findByUserName(targetUser.getUserName());
        UserDmHistory targetDirectMessages = UserProfileControllerService.activeUserDirectMessageHistory(targetUser.getId());
        UserPostHistory targetUserPostHistory = new UserPostHistory();

        /** Profile details on Post history from other parts of the website **/
        List<HiddenPost> targetHiddenPost = UserProfileControllerService.getHiddenPostListForUserBundleMain(targetUser.getId());
        targetUserPostHistory.setViewUserHiddenPost(targetHiddenPost);
        List<ForumPosts> targetForumPost = UserProfileControllerService.getUserForumPost(targetUser.getId());
        targetUserPostHistory.setViewUserForumPost(targetForumPost);
        List<Replies> targetForumReplies = UserProfileControllerService.getUserReplies(targetUser.getId());
        targetUserPostHistory.setViewUserForumReplies(targetForumReplies);
        /** Events need username, userid, or UserEntity inside model **/
        List<Event> targetEventPost = UserProfileControllerService.getUserEventPost(targetUser.getUserName());
        targetUserPostHistory.setViewUserEventPost(targetEventPost);


        /** Retrieve Service Post and set in UserPostHistory Obj **/
        List<Service> targetServicePost = UserProfileControllerService.getUserServicePost(targetUser.getUserName());
        targetUserPostHistory.setViewUserServicePost(targetServicePost);
        /** Services, Resources, RestaurantReview need username, userid, or UserEntity inside model **/

        return new ResponseEntity<>(new UserBundle(targetUser, targetInformation, targetDirectMessages, targetUserPostHistory), HttpStatus.OK);
    }

    /** Returns a list of post hidden relative to the user profile **/
    @GetMapping("/getHiddenPostList/{userId}")
    public List<HiddenPost> getHiddenPostListByUserId(@PathVariable int userId) {
        List<HiddenPost> hiddenPostList = new ArrayList<>();
        for (HiddenPost post : hiddenPostRepository.findAll()) {
            if (post.getUserId() == userId) {
                hiddenPostList.add(post);
            }
        }
        return hiddenPostList;
    }

    /** Returns a filtered list of post that excludes post marked as hidden by the user when their page is viewed **/
    @GetMapping("/getUpdatedPostHistoryViewUser/{userId}")
    public ResponseEntity<?> getUpdatedPostHistoryViewUser(@PathVariable int userId) {

        if (!userRepository.existsById(userId)) {
            return new ResponseEntity<>(new ResponseMessage("404"), HttpStatus.OK);
        }

        List<ForumPosts> currentPostSettings = new ArrayList<>();
        List<HiddenPost> hiddenPostList = new ArrayList<>();
        List<ForumPosts> userPost = new ArrayList<>();

        for (HiddenPost post : hiddenPostRepository.findAll()) {
            if (post.getUserId() == userId) {
                hiddenPostList.add(post);
            }
        }

        for (ForumPosts post : forumPostRepository.findAll()) {
            if (post.getUserEntity().getId() == userId) {
                userPost.add(post);
                currentPostSettings.add(post);
            }
        }

        for (ForumPosts post : userPost) {
            for (HiddenPost hidden : hiddenPostList) {
                if (post.getId() == hidden.getHidePostId()) {
                    currentPostSettings.remove(post);
                }
            }
        }

        return new ResponseEntity<>(currentPostSettings, HttpStatus.OK);
    }

    /** Returns the users profile picture **/
    @GetMapping(path = {"/userProfileImage/{userName}"})
    public ResponseEntity<?> getImageDetails(@PathVariable("userName") String userName) throws IOException {

        final Optional<ProfilePicture> dbImage = profilePictureRepository.findByUserName(userName);
        if (dbImage.isEmpty()) {
            ResponseMessage responseMessage = new ResponseMessage("User does not have a profile picture.");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        return new ResponseEntity<>(ProfilePicture.builder()
                .id(dbImage.get().getId())
                .userName(String.valueOf(UUID.randomUUID()))
                .type(dbImage.get().getType())
                .image(ImageUtility.decompressImage(dbImage.get().getImage())).build(), HttpStatus.OK);
    }


    /** POST REQUEST **/
    /** POST REQUEST **/
    /** POST REQUEST **/

    /** Uploads the image to the profile picture table **/
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file)
            throws IOException {

        if (profilePictureRepository.findByUserName(file.getOriginalFilename()).isPresent()) {
            Optional<ProfilePicture> remove = profilePictureRepository.findByUserName(file.getOriginalFilename());
            profilePictureRepository.deleteById(remove.get().getId());
        }

        profilePictureRepository.save(ProfilePicture.builder()
                .userName(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Image uploaded successfully: " + file.getOriginalFilename()));
    }

    /** Update the hidden post on user profile **/
    @PostMapping("/hidePostList")
    public ResponseEntity<?> hiddenPosts(@RequestBody HidePostDTO hidePostDTO) {

        /** hide post based on their postType, sort through repos to find the right object to hide **/

            for (HiddenPost post : hiddenPostRepository.findAll()) {
                if (Objects.equals(hidePostDTO.getPostType(), post.getPostType()) && Objects.equals(post.getHidePostId(), hidePostDTO.getHidePostId()) && Objects.equals(hidePostDTO.getUserId(), post.getUserId())) {
                    ResponseMessage responseMessage = new ResponseMessage("Post Already Hidden");
                    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
                }
            }

        HiddenPost hiddenPost = new HiddenPost(hidePostDTO.getPostType(), hidePostDTO.getHidePostId(), hidePostDTO.getUserId());
        hiddenPostRepository.save(hiddenPost);

        return new ResponseEntity<>(hiddenPostRepository.findAll(), HttpStatus.OK);
    }

    /** Marks a hidden post as no longer hidden by the main user **/
    @PostMapping("/unHidePost")
    public ResponseEntity<?> unHidePostFromProfile(@RequestBody HidePostDTO hidePostDTO) {

        for (HiddenPost post : hiddenPostRepository.findAll()) {
            if (Objects.equals(hidePostDTO.getHidePostId(), post.getHidePostId()) && Objects.equals(hidePostDTO.getPostType(), post.getPostType()) && Objects.equals(hidePostDTO.getUserId(), post.getUserId())) {
                hiddenPostRepository.delete(post);
                ResponseMessage responseMessage = new ResponseMessage("Post is no longer hidden.");
                return new ResponseEntity<>(responseMessage, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** Saves a direct message to the database **/
    @PostMapping("/sendDirectMessage")
    public ResponseEntity<?> updateUserInformation(@RequestBody DirectMessageDTO directMessageDTO) {

        DirectMessage messageSent = new DirectMessage(directMessageDTO.getReceivedByUserId(),
                                                      directMessageDTO.getReceivedByUserName(),
                                                      directMessageDTO.getSentByUserId(),
                                                      directMessageDTO.getSentByUserName(),
                                                      directMessageDTO.getMessageContent());

        if(messageSent.getMessageContent().toCharArray().length < 3) {
            throw new MinimumCharacterException();
        }

        directMessageRepository.save(messageSent);

        UserDmHistory targetDirectMessages = UserProfileControllerService.activeUserDirectMessageHistory(directMessageDTO.getSentByUserId());
        List<DirectMessage> allDirectMessageHistory = targetDirectMessages.getDirectMessageList();



        return new ResponseEntity<>(allDirectMessageHistory, HttpStatus.OK);

    }

    /** PUT Request **/
    /** PUT Request **/
    /** PUT Request **/


    /** Testing put request: Updates the user information that is displayed on the user profile **/
    @PutMapping("/update-user-information/{userName}")
    public ResponseEntity<?> updateUserInformation(@PathVariable String userName, @RequestBody UserInfoDTO userInfoDTO) {

        Optional<UserInformation> userInfo = userInformationRepository.findByUserName(userName);

        userInfo.get().setFirstName(userInfoDTO.getFirstName());
        userInfo.get().setLastName(userInfoDTO.getLastName());
        userInfo.get().setNeighborhood(userInfoDTO.getNeighborhood());
        userInfo.get().setCity(userInfoDTO.getCity());
        userInfo.get().setState(userInfoDTO.getState());

        userInformationRepository.save(userInfo.get());

        return new ResponseEntity<>(userInfo, HttpStatus.OK);

    }

}
