import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../services/client.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addclient',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './addclient.component.html',
  styleUrl: './addclient.component.css'
})
export class AddclientComponent {

  formData: any = {
    id: '',
    name: '', 
  }

  errorMessage = ''

  constructor (private readonly clientService: ClientService, private readonly router: Router) {

  }

  async handleSubmit () {
    this.formData.id = parseInt(this.formData.id)

    if (!this.formData.id || !this.formData.name) {
      this.showError('Please fill all fields')
      return
    }

    const confirmClient = confirm('Estas seguro que quieres agregar este cliente?')
    if (!confirm) {
      return
    }

    try {
      const token = localStorage.getItem('token')
      
      if (!token) {
        throw new Error('Not token found')
      }

      const response = await this.clientService.addClient(this.formData, token)
      if (response.statusCode === 200) {
        this.router.navigate(['/clients'])
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