import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {

  constructor(private readonly userService: UserService) { }

  isAuthenticated = false
  isAdmin = false
  isUser = false

  ngOnInit(): void {
    this.isAuthenticated = this.userService.isAuthenticated()
    this.isAdmin = this.userService.isAdmin()
    this.isUser = this.userService.isUser()
  }

  logout(): void {
    this.userService.logOut()
    this.isAuthenticated = false
    this.isAdmin = false
    this.isUser = false
  }

}
