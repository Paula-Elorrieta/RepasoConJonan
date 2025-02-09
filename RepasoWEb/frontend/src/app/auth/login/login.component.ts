import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  username: string = 'admin';
  password: string = 'admin';

  constructor(private route: Router) {}

  onLogin() {
    if (!this.username || !this.password) {
      alert('Username eta pasahitza behar dira');
      return;
    }

    if (this.username == 'admin' && this.password == 'admin') {
      alert('Ongi etorri');
      const user = {
        username: this.username,
        password: this.password,
      };
      localStorage.setItem('user', JSON.stringify(user));

      this.route.navigate(['pages/home']);
    } else {
      alert('Ez duzu baimenik');
    }
  }
}
