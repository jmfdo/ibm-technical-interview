import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DeviceService } from '../../services/device.service';

@Component({
  selector: 'app-deviceslist',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './deviceslist.component.html',
  styleUrl: './deviceslist.component.css'
})
export class DeviceslistComponent implements OnInit {
  devices: any[] = []
  errorMessage = ''
  devicesData: any[] = []
  sorted = false
  filtered = false;

  constructor (private readonly deviceService: DeviceService, private readonly router: Router) {

  }
  ngOnInit(): void {
    this.loadDevices()
  }

  switchTable() {
    this.sorted = !this.sorted;
    this.refreshTableData();
  }

  refreshTableData() {
    this.devicesData = this.sorted ? this.sortData([...this.devices]) : [...this.devices];
  }

  sortData(devices: any[]) {
    return devices.sort((a, b) => b['timesRequested'] - a['timesRequested'])
  }

  async loadDevices () {
    try {
      const token: any = localStorage.getItem('token')
      const response = await this.deviceService.getAllDevices(token)
      
      if (response && response.statusCode === 200 && response.devices) {
        this.devices = response.devices
        this.devicesData = response.devices
      } else {
        this.showError('No se encontraron dispositivos')
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