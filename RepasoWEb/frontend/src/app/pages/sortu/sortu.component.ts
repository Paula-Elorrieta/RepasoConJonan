import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QueryService } from '../../services/query.service';
import { Gatito } from '../../interface/gatito';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCard, MatCardModule, MatCardTitle } from '@angular/material/card';
import { ArgazkiPipe } from '../../pipes/argazki.pipe';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-sortu',
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule,
    FormsModule,
    MatCardTitle,
  ],
  templateUrl: './sortu.component.html',
  styleUrl: './sortu.component.css',
})
export class SortuComponent {
  gatito: Gatito = {};

  constructor(private query: QueryService, private router: Router) {}

  sortu() {
    if (this.gatito.nombre == undefined || this.gatito.nombre == '') {
      alert('Izena ezin da hutsik egon');
      return;
    }

    if (this.gatito.edad == undefined || this.gatito.edad == 0) {
      alert('Adina ezin da hutsik egon');
      return;
    }

    if (this.gatito.descripcion == undefined || this.gatito.descripcion == '') {
      alert('Deskribapena ezin da hutsik egon');
      return;
    }

    this.query.addGatito(this.gatito).subscribe((data) => {
      console.log(data);
    });
    this.router.navigate(['/pages/home']);
  }

  cancelar() {
    this.router.navigate(['/pages/home']);
  }
}
