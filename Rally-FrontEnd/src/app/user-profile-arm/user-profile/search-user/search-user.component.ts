import { Component, OnInit } from '@angular/core';
import { UserEntity } from '../../models/UserEntity';
import { ViewUserService } from '../services/view-user.service';
import { NgForm } from '@angular/forms';
import { AuthorizeService } from 'src/app/security/security-service/authorize.service';
import { StorageService } from 'src/app/security/security-service/storage-service.service';
@Component({
  selector: 'app-search-user',
  templateUrl: './search-user.component.html',
  styleUrls: ['./search-user.component.css']
})
export class SearchUserComponent implements OnInit {

  /* Search User Variables */
  userList: UserEntity[];
  profilePics: any[];
  dbImage: any[] = [];
  logInStatus: boolean;
  characterError: boolean = false;

  constructor(private userService: ViewUserService,
              private authorize: AuthorizeService,
              private storageService: StorageService) { }

  ngOnInit(): void {
    /* Makes sure user is logged in before */
    if (this.authorize.isloggedIn() !== true) {
      this.authorize.logOut();
    }
    
    this.userService.getUserList().subscribe((data: any) => {
      console.log(data)
      this.userList = data.userNames;
      this.profilePics = data.profilePics;

      // /* Remove active user from list */
      this.userList = this.userList.filter((user: UserEntity) => user.userName !== this.storageService.getUserName());

      /* Make a list of objects with user name and user image for thumbnail display*/
      let makeThumbNails = this.userList;
      for (let pic of this.profilePics) {
        for (let user of makeThumbNails) {
          if (user.userName === pic.userId) {
            let picAndName = {
              userName: user.userName,
              image: 'data:image/jpeg;base64,' + pic.image
            }
            this.dbImage.push(picAndName);
            makeThumbNails = makeThumbNails.filter((user: UserEntity) => user.userName !== picAndName.userName)
          }
        }
      }
      /* Add remaining users to the list who don't have images */
      for (let user of makeThumbNails) {
        let picAndName = {
          userName: user.userName,
          image: null
        }
        this.dbImage.push(picAndName);
      }
      
    })
  }

  generateImage(image) {
    let imageObj = 'data:image/jpeg;base64,' + image.image;
    return imageObj;
  }


  /* Search for specific user by name or by character */
  /* This method needs to be refactored to be handled by backend */
  searchForUser(searchUser: NgForm) {
    this.characterError=false;

    if (searchUser.value.search === undefined || searchUser.value.search.length < 1 || searchUser.value.search.length > 25) {
      this.characterError = true;
      return;
    }

    let filterUser: any[] = [];
    let search = searchUser.value.search.toLowerCase().split('');

    /* This looks for exact matches */
    for (let i =0; i < this.userList.length; i++) {
      if (this.userList[i].userName.toLowerCase() === searchUser.value.search.toLowerCase()) {
        filterUser.push(this.userList[i])
        return this.userList = filterUser;
      }
    }
    
    /* This selects matches that have the same characters as the search input */
    for (let char of search) {
      for (let i = 0; i < this.userList.length; i++) {
        if (this.userList[i].userName.toLowerCase().split('').includes(char)) {
          if (!filterUser.includes(this.userList[i])) {
          filterUser.push(this.userList[i]);
        } else {
          console.log("skip")
        }
        }
      }
    }
    return this.userList = filterUser;
  }

  /* Reset results */
  resetResults() {
    this.characterError = false;
    this.userService.getUserList().subscribe((data: UserEntity[]) => {
      this.userList = data;
      let remove: UserEntity;
      for (let i = 0; i < data.length; i++) {
        if (data[i].userName === localStorage.getItem("userName")) {
          remove = this.userList[i];
        }
      }
      return this.userList = this.userList.filter((user: UserEntity) => user !== remove);
    })
  }

  logOut() {
    this.authorize.logOut()
  }
}