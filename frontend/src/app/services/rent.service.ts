import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RentService {

  private BASE_URL = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  async getAllRents (token: string) {
    const url = `${this.BASE_URL}/adminuser/get-all-rents`
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

  async addRent(userData: any, token: string): Promise<any> {
    
    const url = `${this.BASE_URL}/adminuser/add-rent`
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
