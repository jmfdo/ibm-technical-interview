import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  constructor (private readonly userService: UserService, private readonly router: Router){

  }

  profileInfo: any
  errorMessage = ''

  async ngOnInit() {
    try {
      const token = localStorage.getItem('token')

      if(!token) {
        throw new Error('No token found')
      }

      this.profileInfo = await this.userService.getYourProfile(token)
      console.log(this.profileInfo)
    } catch (error: any) {
      this.showError(error.message)
    }
  }

  updateProfile(id: string) {
    this.router.navigate(['/update', id])
  }

  showError (message: string) {
    this.errorMessage =message
    setTimeout(() => {
      this.errorMessage = ''
    }, 3000)
  }

}
