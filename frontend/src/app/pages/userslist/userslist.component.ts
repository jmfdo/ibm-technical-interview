import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';

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

  constructor (private readonly userService: UserService, private readonly router: Router) {

  }

  ngOnInit(): void {
    this.loadUsers()
  }

  async loadUsers() {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.userService.getAllUsers(token)
      
      if (response && response.statusCode === 200 && response.users) {
        this.users = response.users
      } else {
        this.showError('No se encontraron usuarios')
      }
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  async deleteUser (userId: string) {
    const confirmDelete = confirm('Estas seguro que quieres borrar este usuario?')
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

  showError (message: string) {
    this.errorMessage = message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}
