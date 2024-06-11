import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor (private readonly userService: UserService, private router: Router) {

  }

  email = ''
  password = ''
  errorMessage = ''

  async handleSubmit() {
    if (!this.email || !this.password) {
      this.errorMessage = "Email and password required"
      return
    }

    try {
      const response = await this.userService.login(this.email, this.password)
      if (response.statusCode === 200) {
        localStorage.setItem('token', response.token)
        localStorage.setItem('role', response.role)
        this.router.navigate(['/profile'])
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
