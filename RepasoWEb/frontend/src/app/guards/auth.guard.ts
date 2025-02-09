import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = localStorage.getItem('user');
    const userParse = JSON.parse(localStorage.getItem('user') || '{}');

    if (user) {
      return true;
    } else {
      this.router.navigate(['auth/login']);
      return false;
    }

    // if (userParse && userParse.username === 'admin' && userParse.password === 'admin') {
    // 	return true;
    //   } else {
    // 	this.router.navigate(['auth/login']);
    // 	return false;
    //   }
  }
}
