import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RentService } from '../../services/rent.service';

@Component({
  selector: 'app-rentslist',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './rentslist.component.html',
  styleUrl: './rentslist.component.css'
})
export class RentslistComponent implements OnInit {

  constructor (private readonly rentService: RentService, private readonly router: Router) {

  }

  states: any = {
    PENDING: "PENDIENTE",
    ACTIVE: "ACTIVO",
    EXPIRED: "VENCIDO",
    OVERDUE: "CON RETRASO"
  }

  rents: any[] = []
  errorMessage = ''
  ngOnInit(): void {
      this.loadRents()
  }

  showState () {
    
  }
  async loadRents () {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.rentService.getAllRents(token)
      
      if (response && response.statusCode === 200 && response.rents) {
        this.rents = response.rents
      } else {
        this.showError('No se encontraron dispositivos alquilados')
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




