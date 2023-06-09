import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthorizeService } from 'src/app/security/security-service/authorize.service';
import { ThemeserviceService } from 'src/app/services/themeservice.service';
import { ViewUserService } from 'src/app/user-profile-arm/user-profile/services/view-user.service';

@Component({
  selector: 'app-forum-search-results',
  templateUrl: './forum-search-results.component.html',
  styleUrls: ['./forum-search-results.component.css']
})
export class ForumSearchResultsComponent implements OnInit {

  currentUser: String;
  logInStatus: Boolean;
  darktheme: Boolean;
  testArray;
  newArray;
  createPostBoolean: boolean;
  loginLoading: boolean;
  constructor(private http: HttpClient, private router: Router, private themeservice: ThemeserviceService , private authorize: AuthorizeService, private activeUserService: ViewUserService) {
    this.logInStatus = false;
    this.createPostBoolean = false;
    this.darktheme = false;
    this.testArray;
    this.newArray = [];
    this.loginLoading = true;
   }
  
  ngOnInit(): void {
    if (this.authorize.isloggedIn() === true) {
      
      /* Get all information relevent to user */
      this.activeUserService.getMainUserBundleByUserName(this.themeservice.getUserName())
      .subscribe((data: any) => {
        this.logInStatus = true;
        this.currentUser = data.viewUser.userName
        this.loginLoading = false;
      },  err => {
        if (err.status === 500) {
          this.logInStatus = false;
          this.currentUser = null;
          this.themeservice.logOut();
          this.loginLoading = false;
        }
      })
  }
    else {
      this.themeservice.logOut();
      this.logInStatus = false;
      this.loginLoading = false;
  }
    this.checkTheme();
    this.getPosts();
  }
  checkTheme(){
      if (localStorage.getItem('theme') == 'dark'){
          this.Dark();
      }
  }

  getPosts(){
    if (localStorage.getItem('searchTerm') != null){
    this.themeservice.getAllForumPosts().subscribe((posts) =>{
      this.newArray = this.themeservice.searchPosts(posts)
      if(this.newArray.length == 0){
        document.getElementById("title").innerHTML = "No Results"
      }}) 
    }
    else {
      document.getElementById("title").innerHTML = "No Results"
    }
  }

  Light(){
    localStorage.setItem('theme', 'light'); 
      this.darktheme = false;
  }
  Dark(){
    localStorage.setItem('theme', 'dark'); 
    this.darktheme = true;
  }
  logOut() {
    this.logInStatus = false;
    this.themeservice.logOut();
  }
}
