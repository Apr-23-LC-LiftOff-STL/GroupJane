import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventViewComponent } from './Events/event-view/event-view.component';
import { ForumComponent } from './community-forum-arm/forum/forum.component';
import { ResourceComponent } from './resources-arm/resource/resource.component';
import { ResourceAddComponent } from './resources-arm/resource-add/resource-add.component';
import { ResourceUpdateComponent } from './resources-arm/resource-update/resource-update.component';
import { RestaurantsHomepageComponent } from './restaurants-homepage/restaurants-homepage.component';
import { RestaurantAmericanComponent } from './restaurants-homepage/restaurant-american/restaurant-american.component';
import { RestaurantAsianComponent } from './restaurants-homepage/restaurant-asian/restaurant-asian.component';
import { RestaurantsMexicanComponent } from './restaurants-homepage/restaurants-mexican/restaurants-mexican.component';
import { RestaurantsItalianComponent } from './restaurants-homepage/restaurants-italian/restaurants-italian.component';
import { RestaurantsPizzaComponent } from './restaurants-homepage/restaurants-pizza/restaurants-pizza.component';
import { EventComponent } from './Events/event/event.component';
import { EventCreateComponent } from './Events/event-create/event-create.component';
import { ServicesHomeComponent } from './services-arm/services-home/services-home.component';
import { OfferComponent } from './services-arm/offer/offer.component';
import { IntroductionsComponent } from './community-forum-arm/forum/introductions/introductions.component';
import { ForumTopic1Component } from './community-forum-arm/forum/forum-topic1/forum-topic1.component';
import { ForumTopic2Component } from './community-forum-arm/forum/forum-topic2/forum-topic2.component';
import { ForumTopic3Component } from './community-forum-arm/forum/forum-topic3/forum-topic3.component';
import { ViewPostComponent } from './community-forum-arm/forum/view-post/view-post.component';
import { ViewUserProfileComponent } from './user-profile-arm/user-profile/view-user-profile/view-user-profile.component';
import { CommunityHomeComponent } from './community-forum-arm/forum/community-home/community-home.component';
import { ResourceSearchComponent } from './resources-arm/resource-search/resource-search.component';
import { ViewRestaurantComponent } from './restaurants-homepage/view-restaurant/view-restaurant.component';
import { SearchRestaurantComponent } from './restaurants-homepage/search/search-restaurant.component';
import { ForumSearchResultsComponent } from './community-forum-arm/forum/forum-search-results/forum-search-results.component';
import { SearchComponent } from './services-arm/search/search.component';
import { ServiceItemComponent } from './services-arm/service-item/service-item.component';
import { EventEditComponent } from './Events/event-edit/event-edit.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './user-profile-arm/exception/page-not-found/page-not-found.component';
import { EmailVerificationComponent } from './security/email-verification/email-verification.component';
import { EventJoinComponent } from './Events/event-join/event-join.component';
import { JoinEditComponent } from './Events/join-edit/join-edit.component';
import { SearchUserComponent } from './user-profile-arm/user-profile/search-user/search-user.component';
import { RegisterUserComponent } from './user-profile-arm/login-register/register-user/register-user.component';
import { LoginUserComponent } from './user-profile-arm/login-register/login-user/login-user.component';
import { UserProfileComponent } from './user-profile-arm/user-profile/user-profile-main/user-profile.component';



const routes: Routes = [
  {path: 'login', component: LoginUserComponent},
  {path: 'register', component: RegisterUserComponent},
  {path: 'search', component: SearchUserComponent},
  {path: 'events', component: EventViewComponent},
  {path: 'forum', component: ForumComponent},
  {path: 'communityHomeForum', component: CommunityHomeComponent},
  {path: 'resource', component: ResourceComponent},
  {path: 'resource/:id', component: ResourceComponent },
  {path: 'add', component: ResourceAddComponent},
  {path: 'resources', component: ResourceSearchComponent},
  {path: 'resource/update', component: ResourceUpdateComponent},
  {path: 'update/:id', component: ResourceUpdateComponent},
  {path: 'restaurants' , component: RestaurantsHomepageComponent},
  {path: 'restaurants/american' , component: RestaurantAmericanComponent},
  {path: 'restaurants/asian' , component: RestaurantAsianComponent},
  {path: 'restaurants/mexican' , component: RestaurantsMexicanComponent},
  {path: 'restaurants/italian' , component: RestaurantsItalianComponent},
  {path: 'restaurants/pizza' , component: RestaurantsPizzaComponent},
  {path: 'event', component: EventComponent },
  {path: 'event/:id', component: EventComponent },
  {path: 'create', component: EventCreateComponent},
  {path: 'services', component: ServicesHomeComponent},
  {path: 'offer', component: OfferComponent},
  {path: 'forum/communityhome', component: CommunityHomeComponent},
  {path: 'forum/introductions', component: IntroductionsComponent},
  {path: 'forum/topic1', component: ForumTopic1Component},
  {path: 'forum/topic2', component: ForumTopic2Component},
  {path: 'forum/topic3', component: ForumTopic3Component},
  {path: 'forum/post/:id', component: ViewPostComponent},
  {path: 'myProfile', component: UserProfileComponent},
  {path: 'user/:userName', component: ViewUserProfileComponent},
  {path: 'restaurant/:id', component: ViewRestaurantComponent},
  {path: 'restaurants/search', component: SearchRestaurantComponent},
  {path: 'forum/search', component: ForumSearchResultsComponent},
  {path: 'searchservice', component: SearchComponent},
  {path: 'serviceitem', component: ServiceItemComponent},
  {path: 'edit/:id', component: EventEditComponent},
  {path: 'home', component: HomeComponent},
  {path: 'user/404', component: PageNotFoundComponent},
  {path: 'confirm-account/:token', component: EmailVerificationComponent},
  {path: 'invalidUser/404', component: PageNotFoundComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full' },
  {path: 'confirm-account/:token', component: EmailVerificationComponent},
  {path: 'join/:id', component: EventJoinComponent}, 
  {path: 'edit/join/:id', component: JoinEditComponent},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }