import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { UserslistComponent } from './pages/userslist/userslist.component';
import { adminGuard, usersGuard } from './guards/users.guard';
import { RentslistComponent } from './pages/rentslist/rentslist.component';
import { ClientslistComponent } from './pages/clientslist/clientslist.component';
import { DeviceslistComponent } from './pages/deviceslist/deviceslist.component';
import { AddrentComponent } from './pages/addrent/addrent.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent, canActivate: [adminGuard] },
  { path: 'users', component: UserslistComponent, canActivate: [adminGuard] },
  { path: 'rents', component: RentslistComponent, canActivate: [usersGuard] },
  { path: 'clients', component: ClientslistComponent, canActivate: [usersGuard] },
  { path: 'devices', component: DeviceslistComponent, canActivate: [usersGuard] },
  { path: 'add-rent', component: AddrentComponent, canActivate: [usersGuard] },
  { path: '**', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
