import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private BASE_URL = "http://localhost:1010";
  constructor(private http: HttpClient) { }

  async getAllClients (token: string) {
    const url = `${this.BASE_URL}/adminuser/get-all-clients`
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
}
