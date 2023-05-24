package org.rally.backend.userprofilearm.utility;

import org.rally.backend.eventsarm.models.Event;
import org.rally.backend.eventsarm.repository.EventRepository;
import org.rally.backend.forumarm.models.ForumPosts;
import org.rally.backend.forumarm.models.Replies;
import org.rally.backend.forumarm.repository.ForumPostRepository;
import org.rally.backend.forumarm.repository.RepliesRepository;
import org.rally.backend.servicesarm.repository.ServiceRepository;
import org.rally.backend.userprofilearm.model.*;
import org.rally.backend.userprofilearm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserProfileControllerService {

    static UserRepository userRepository;
    static UserInformationRepository userInformationRepository;
    static RoleRepository roleRepository;
    static DirectMessageRepository directMessageRepository;
    static ProfilePictureRepository profilePictureRepository;
    static ForumPostRepository forumPostRepository;
    static RepliesRepository repliesRepository;
    static HiddenPostRepository hiddenPostRepository;
    static ServiceRepository serviceRepository;
    static EventRepository eventRepository;


    @Autowired
    public UserProfileControllerService(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        UserInformationRepository userInformationRepository,
                                        DirectMessageRepository directMessageRepository,
                                        ProfilePictureRepository profilePictureRepository,
                                        ForumPostRepository forumPostRepository,
                                        RepliesRepository repliesRepository,
                                        HiddenPostRepository hiddenPostRepository,
                                        ServiceRepository serviceRepository,
                                        EventRepository eventRepository) {
        UserProfileControllerService.userRepository = userRepository;
        UserProfileControllerService.roleRepository = roleRepository;
        UserProfileControllerService.userInformationRepository = userInformationRepository;
        UserProfileControllerService.directMessageRepository = directMessageRepository;
        UserProfileControllerService.profilePictureRepository = profilePictureRepository;
        UserProfileControllerService.forumPostRepository = forumPostRepository;
        UserProfileControllerService.repliesRepository = repliesRepository;
        UserProfileControllerService.hiddenPostRepository = hiddenPostRepository;
        UserProfileControllerService.serviceRepository = serviceRepository;
        UserProfileControllerService.eventRepository = eventRepository;
    }

    /** This method is the list that displays the users post history when viewing a user profile
     * It doesn't display a post marked as hidden by the user account being viewed **/
    public static List<CurrentUserPostHistory> sortUpdatedPostHistoryViewUser(int userId) {

        Optional<UserEntity> user = userRepository.findById(userId);
        List<HiddenPost> hiddenPostList = new ArrayList<>();

        List<CurrentUserPostHistory> currentUserPostHistories = new ArrayList<>();

        for (HiddenPost post : hiddenPostRepository.findAll()) {
            if (post.getUserId() == userId) {
                hiddenPostList.add(post);
            }
        }

        for (ForumPosts post : forumPostRepository.findAll()) {
            if (post.getUserEntity().getId() == userId) {
                currentUserPostHistories.add(new CurrentUserPostHistory(post.getId(), "ForumPost", post.getTitle(), false, post.getDescription()));
            }
        }
        for (Event event : eventRepository.findAll()) {
            if (Objects.equals(event.getEventHost(), user.get().getUserName())) {
                currentUserPostHistories.add(new CurrentUserPostHistory(event.getId(), "Event", event.getEventTitle(), false, event.getDescription()));
            }
        }

        for (CurrentUserPostHistory post : currentUserPostHistories) {
            for (HiddenPost hide : hiddenPostList) {
                if (Objects.equals(post.getId(), hide.getHidePostId()) && Objects.equals(post.getType(), hide.getPostType())) {
                    post.setHidden(true);
                }
            }
        }

        return currentUserPostHistories;
    }

    /** This method returns 2 lists in an object.
     * allUsers is a list of all the users the main account has interacted with.
     * allMessagesRelatedToUser is a list all message history with the main user.
     * Pending: Need to make a UserDmHistory specifically for viewing a user (only dms with user and viewed user). **/
    public static UserDmHistory activeUserDirectMessageHistory(int id) {

        /** Isolating all messages from and to user **/
        List<UserEntity> allUsers = new ArrayList<>();
        List<DirectMessage> allMessagesRelatedToUser = new ArrayList<>();

        for (DirectMessage dm : directMessageRepository.findAll()) {
            if (dm.getReceivedByUserId().equals(id) || dm.getSentByUserId().equals(id)) {

                if (allMessagesRelatedToUser.size() == 0) {
                    allUsers.add(userRepository.findByUserName(dm.getReceivedByUserName()));
                    allUsers.add(userRepository.findByUserName(dm.getSentByUserName()));
                }

                allMessagesRelatedToUser.add(dm);

                if (!allUsers.contains(userRepository.findByUserName(dm.getReceivedByUserName()))){
                    allUsers.add(userRepository.findByUserName(dm.getReceivedByUserName()));
                } else if (!allUsers.contains(userRepository.findByUserName(dm.getSentByUserName()))) {
                    allUsers.add(userRepository.findByUserName(dm.getSentByUserName()));
                }
            }
        }
        return new UserDmHistory(allUsers, allMessagesRelatedToUser);
    }


    /** This method returns a lists of HiddenPost objects **/
    public static List<HiddenPost> getHiddenPostListForUserBundleMain(int userId) {
        List<HiddenPost> hiddenPostList = new ArrayList<>();
        for (HiddenPost post : hiddenPostRepository.findAll()) {
            if (post.getUserId() == userId) {
                hiddenPostList.add(post);
            }
        }
        return hiddenPostList;
    }

    /** This method returns a lists of ForumPosts objects **/
    public static List<ForumPosts> getUserForumPost(int userId) {
        List<ForumPosts> targetForumPost = new ArrayList<>();
        for (ForumPosts forumPosts : forumPostRepository.findAll()) {
            if (forumPosts.getUserEntity().getId() == userId) {
                targetForumPost.add(forumPosts);
            }
        }
        return targetForumPost;
    }

    /** This method returns a lists of Event objects **/
    public static List<Event> getUserEventPost(String userName) {
        List<Event> targetEventPost = new ArrayList<>();
        for (Event eventPosts: eventRepository.findAll()) {
            if (Objects.equals(eventPosts.getEventHost(), userName)) {
                targetEventPost.add(eventPosts);
            }
        }
        return targetEventPost;
    }

    /** This method returns a lists of Forum Replies objects **/
    public static List<Replies> getUserReplies(int userId){
        List<Replies> targetForumPostReplies = new ArrayList<>();
        for (Replies forumReplies: repliesRepository.findAll()) {
            if (forumReplies.getUserEntity().getId() == userId) {
                targetForumPostReplies.add(forumReplies);
            }
        }
        return targetForumPostReplies;
    }

    public static List<org.rally.backend.servicesarm.model.response.Service> getUserServicePost(String userName) {
        List<org.rally.backend.servicesarm.model.response.Service> targetServicePost = new ArrayList<>();
        for (org.rally.backend.servicesarm.model.response.Service service : serviceRepository.findAll()) {
            if (Objects.equals(service.getName(), userName)) {
                targetServicePost.add(service);
            }

        }
        return targetServicePost;
    }
}
