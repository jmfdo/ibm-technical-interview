import { CanActivateFn, Router } from '@angular/router';
import { UserService } from './user.service';
import { inject } from '@angular/core';

export const usersGuard: CanActivateFn = (route, state) => {
  if (inject(UserService).isAuthenticated()) {
    return true
  } else {
    inject(Router).navigate(['/login'])
    return false
  }
};

export const adminGuard: CanActivateFn = (route, state) => {
  if (inject(UserService).isAdmin()) {
    return true
  } else {
    inject(Router).navigate(['/login'])
    return false
  }
};