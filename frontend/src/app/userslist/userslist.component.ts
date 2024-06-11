import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-userslist',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './userslist.component.html',
  styleUrl: './userslist.component.css'
})
export class UserslistComponent implements OnInit {

  users: any[] = []
  errorMessage = ''
  profileId: any

  constructor (private readonly userService: UserService, private readonly router: Router) {

  }

  ngOnInit(): void {
    this.loadUsers()
  }

  async loadUsers() {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.userService.getAllUsers(token)
      this.profileId =await this.userService.getYourProfile(token)
      
      if (response && response.statusCode === 200 && response.usersList) {
        this.users = response.usersList
      } else {
        this.showError('No users found')
      }
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  async deleteUser (userId: string) {
    const confirmDelete = confirm('Are you sure you wanna delete this user?')
    if (confirmDelete) {
      try {
        const token: any = localStorage.getItem('token')
        await this.userService.deleteUser(userId, token)
        this.loadUsers
      } catch (error: any) {
        this.showError(error.message)
      }
    }
  }

  navigateToUpdate(userId: string) {
    this.router.navigate(['/update', userId])
  }

  showError (message: string) {
    this.errorMessage = message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}
