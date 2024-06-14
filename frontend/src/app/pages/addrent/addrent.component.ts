import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RentService } from '../../services/rent.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-addrent',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './addrent.component.html',
  styleUrl: './addrent.component.css'
})
export class AddrentComponent {

  formData: any = {
    deviceId: '',
    clientId: '',
    userId: '',
    rentDate: '',
    expDate: ''
  }

  errorMessage = ''

  constructor(private readonly rentService: RentService, private readonly router: Router, private readonly userService: UserService) { }

  async handleSubmit() {
    this.formData.deviceId = parseInt(this.formData.deviceId)
    this.formData.clientId = parseInt(this.formData.clientId)
    this.formData.userId = parseInt(localStorage.getItem('userId')!)
    if (!this.formData.deviceId || !this.formData.clientId || !this.formData.userId || !this.formData.rentDate || !this.formData.expDate) {
      this.showError('Please fill in all fields.')
      console.log(this.formData)
      return
    }

    const confirmRent = confirm('Estas seguro que quieres agregar este alquiler?')
    if (!confirmRent) {
      return
    }

    try {
      const token = localStorage.getItem('token')
      if (!token) {
        throw new Error('No token found')
      }

      const response = await this.rentService.addRent(this.formData, token)
      if (response.statusCode === 200) {
        this.router.navigate(['/rents'])
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

