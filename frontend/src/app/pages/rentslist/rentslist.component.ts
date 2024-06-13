import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RentService } from '../../services/rent.service';

@Component({
  selector: 'app-rentslist',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './rentslist.component.html',
  styleUrl: './rentslist.component.css'
})
export class RentslistComponent implements OnInit {

  constructor (private readonly rentService: RentService, private readonly router: Router) {}

  rents: any[] = []
  errorMessage = ''
  ngOnInit(): void {
      this.loadRents()
  }

  async loadRents () {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.rentService.getAllRents(token)
      
      if (response && response.statusCode === 200 && response.rents) {
        this.rents = response.rents
      } else {
        this.showError('No users found')
      }
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  showError (message: string) {
    this.errorMessage = message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }
}




