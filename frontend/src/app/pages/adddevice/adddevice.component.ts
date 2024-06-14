import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DeviceService } from '../../services/device.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adddevice',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './adddevice.component.html',
  styleUrl: './adddevice.component.css'
})
export class AdddeviceComponent {
  formData: any = {
    name: '',
    deviceType: '',
    amount: '',
  }

  errorMessage = ''

  constructor(private readonly deviceService: DeviceService, private readonly router: Router) {}
  
  async handleSubmit () {
    this.formData.amount = parseInt(this.formData.amount)

    if (!this.formData.name || !this.formData.deviceType || !this.formData.amount) {
      this.showError('Please fill in all fields.')
      return
    }

    const confirmDevice = confirm("Esta seguro que quiere agregar este dispositivo?")
    if (!confirm) {
      return
    }

    try {
      const token = localStorage.getItem('token')
      
      if (!token) {
        throw new Error('Not token found')
      }

      const response = await this.deviceService.addDevice(this.formData, token)
      if (response.statusCode === 200) {
        this.router.navigate(['/devices'])
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

