import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { UserService } from '../../services/user.service'
import { Router } from '@angular/router'

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  formData: any = {
    name: '',
    email: '',
    password: '',
    role: '',
  }

  errorMessage = ''

  constructor (private readonly userService: UserService, private readonly router: Router) {
    
  }

  async handleSubmit () {
    if (!this.formData.name || !this.formData.email || !this.formData.password || !this.formData.role) {
      this.showError('Please fill in all fields.')
      return
    }

    // Confirm registration with user
    const confirmRegistration = confirm('Are you sure you want to register this user?')
    if (!confirmRegistration) {
      return
    }

    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error('No token found')
      }

      const response = await this.userService.register(this.formData, token)
      if (response.statusCode === 200) {
        this.router.navigate(['/users'])
      } else {
        this.showError(response.message)
      }
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  showError(message: string) {
    this.errorMessage = message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}
