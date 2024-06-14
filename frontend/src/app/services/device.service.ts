import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private BASE_URL = "http://localhost:1010";
  constructor(private http: HttpClient) { }

  async getAllDevices (token: string) {
    const url = `${this.BASE_URL}/adminuser/get-all-devices`
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    try {
      const response = await this.http.get<any>(url, {headers}).toPromise()
      return response
    } catch (error) {
      throw error;
    }
  }

  async addDevice(userData: any, token: string): Promise<any> {
    
    const url = `${this.BASE_URL}/adminuser/add-device`
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    })
    try {
      const response = await this.http.post<any>(url, userData, { headers }).toPromise()
      return response
    } catch (error) {
      throw error
    }
  }
}
