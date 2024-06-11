import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-updateuser',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './updateuser.component.html',
  styleUrl: './updateuser.component.css'
})
export class UpdateuserComponent implements OnInit {

  constructor(private readonly userService: UserService, private readonly router: Router, private readonly route: ActivatedRoute) {}

  userId: any
  userData: any = {}
  errorMessage = ''

  ngOnInit(): void {
    this.getUserById()
  }

  async getUserById() {
    this.userId = this.route.snapshot.paramMap.get('id')
    const token = localStorage.getItem('token')

    if(!this.userId || !token) {
      this.showError("User ID or token is required")
      return
    }

    try {
      let userDataResponse = await this.userService.getUserById(this.userId, token)
      const { name, email, role } = userDataResponse.users
      this.userData = userDataResponse
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  async updateUser() {
    const confirmUpdate = confirm("Are you sure you wanna update this user")
    if (!confirmUpdate) return
    
    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error('Token not found')
      }
  
      const response = await this.userService.updateUser(this.userId, this.userData, token)

      if (response.statusCode === 200) {
        this.router.navigate(['/users'])
      } else {
        this.showError(response.message)
      }
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  showError (message: string) {
    this.errorMessage =message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}
