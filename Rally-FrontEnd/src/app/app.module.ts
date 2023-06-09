import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { EventViewComponent } from './Events/event-view/event-view.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { EventComponent } from './Events/event/event.component';
import { EventCreateComponent } from './Events/event-create/event-create.component';
import { ForumComponent } from './community-forum-arm/forum/forum.component';
import { ResourceComponent } from './resources-arm/resource/resource.component';
import { ResourceAddComponent } from './resources-arm/resource-add/resource-add.component';
import { ResourceUpdateComponent } from './resources-arm/resource-update/resource-update.component';
import { ServicesHomeComponent } from './services-arm/services-home/services-home.component';
import { OfferComponent } from './services-arm/offer/offer.component';
import { CommunityHomeComponent } from './community-forum-arm/forum/community-home/community-home.component';
import { IntroductionsComponent } from './community-forum-arm/forum/introductions/introductions.component';
import { ForumTopic1Component } from './community-forum-arm/forum/forum-topic1/forum-topic1.component';
import { ForumTopic2Component } from './community-forum-arm/forum/forum-topic2/forum-topic2.component';
import { ThemeserviceService } from './services/themeservice.service';
import { ForumTopic3Component } from './community-forum-arm/forum/forum-topic3/forum-topic3.component';
import { ViewPostComponent } from './community-forum-arm/forum/view-post/view-post.component';
import { ViewUserProfileComponent } from './user-profile-arm/user-profile/view-user-profile/view-user-profile.component';
import { ResourceSearchComponent } from './resources-arm/resource-search/resource-search.component';
import { ViewRestaurantComponent } from './restaurants-homepage/view-restaurant/view-restaurant.component';
import { ReviewComponent } from './restaurants-homepage/review/review.component';
import { SearchRestaurantComponent } from './restaurants-homepage/search/search-restaurant.component';
import { ForumSearchResultsComponent } from './community-forum-arm/forum/forum-search-results/forum-search-results.component';
import { SearchComponent } from './services-arm/search/search.component';
import { SortableHeaderDirective } from './services-arm/models/Sortable';
import { ServicePipe } from './services-arm/models/Service.Pipe';
import { ServiceItemComponent } from './services-arm/service-item/service-item.component'
import { EventEditComponent } from './Events/event-edit/event-edit.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCardModule } from '@angular/material/card';
import { MatNativeDateModule } from '@angular/material/core';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './user-profile-arm/exception/page-not-found/page-not-found.component';
import { httpInterceptorProviders } from './security/Interceptor/jwt-handler.interceptor';
import { EmailVerificationComponent } from './security/email-verification/email-verification.component';
import { EventJoinComponent } from './Events/event-join/event-join.component';
import { JoinEditComponent } from './Events/join-edit/join-edit.component';
import { RestaurantsHomepageComponent } from './restaurants-homepage/restaurants-homepage.component';
import { RestaurantAmericanComponent } from './restaurants-homepage/restaurant-american/restaurant-american.component';
import { RestaurantAsianComponent } from './restaurants-homepage/restaurant-asian/restaurant-asian.component';
import { RestaurantsMexicanComponent } from './restaurants-homepage/restaurants-mexican/restaurants-mexican.component';
import { RestaurantsItalianComponent } from './restaurants-homepage/restaurants-italian/restaurants-italian.component';
import { RestaurantsPizzaComponent } from './restaurants-homepage/restaurants-pizza/restaurants-pizza.component';
import { SecurityComponent } from './security/security.component';
import { SearchUserComponent } from './user-profile-arm/user-profile/search-user/search-user.component';
import { RegisterUserComponent } from './user-profile-arm/login-register/register-user/register-user.component';
import { LoginUserComponent } from './user-profile-arm/login-register/login-user/login-user.component';
import { UserProfileComponent } from './user-profile-arm/user-profile/user-profile-main/user-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterUserComponent,
    LoginUserComponent,
    UserProfileComponent,
    SearchUserComponent,
    EventViewComponent,
    ForumComponent,
    CommunityHomeComponent,
    ResourceComponent,
    ResourceAddComponent,
    ResourceSearchComponent,
    ResourceUpdateComponent,
    RestaurantsHomepageComponent,
    RestaurantAmericanComponent,
    RestaurantAsianComponent,
    RestaurantsMexicanComponent,
    RestaurantsItalianComponent,
    RestaurantsPizzaComponent,
    EventComponent,
    EventCreateComponent,
    ServicesHomeComponent,
    OfferComponent,
    IntroductionsComponent,
    ForumTopic1Component,
    ForumTopic2Component,
    ForumTopic3Component,
    ViewPostComponent,
    ViewUserProfileComponent,
    UserProfileComponent,
    SearchComponent,
    ForumSearchResultsComponent,
    ViewRestaurantComponent,
    ReviewComponent,
    SearchComponent,
    ForumSearchResultsComponent,
    SearchRestaurantComponent,
    SortableHeaderDirective,
    ServicePipe,
    SecurityComponent,
    ServiceItemComponent,
    EventEditComponent,
    HomeComponent,
    PageNotFoundComponent,
    EmailVerificationComponent,
    EventJoinComponent,
    JoinEditComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NoopAnimationsModule,
    MatDatepickerModule,
    MatCardModule,
    MatNativeDateModule,
  ],
  providers: [CookieService, ThemeserviceService, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }