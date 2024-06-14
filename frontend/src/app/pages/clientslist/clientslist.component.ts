import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ClientService } from '../../services/client.service';

@Component({
  selector: 'app-clientslist',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './clientslist.component.html',
  styleUrl: './clientslist.component.css'
})
export class ClientslistComponent implements OnInit {
  clients: any[] = []
  errorMessage = ''
  sorted = false
  clientsData: any[] = []

  constructor (private readonly clientService: ClientService, private readonly router: Router) {}

  ngOnInit(): void {
    this.loadClients()
  }

  switchTable() {
    this.sorted = !this.sorted;
    this.refreshTableData();
  }

  refreshTableData() {
    this.clientsData = this.sorted ? this.sortData([...this.clients]) : [...this.clients];
  }

  sortData(clients: any[]) {
    return clients.sort((a, b) => b['timesRented'] - a['timesRented'])
  }

  async loadClients() {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.clientService.getAllClients(token)
      
      if (response && response.statusCode === 200 && response.clients) {
        this.clients = response.clients
        this.clientsData = response.clients
      } else {
        this.showError('No se encontraron clientes')
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