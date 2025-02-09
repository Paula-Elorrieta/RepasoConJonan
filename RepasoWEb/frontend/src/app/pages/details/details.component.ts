import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { QueryService } from '../../services/query.service';
import { Gatito } from '../../interface/gatito';
import { MatCardModule, MatCardTitle } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ArgazkiPipe } from '../../pipes/argazki.pipe';
import { Refugio } from '../../interface/refugio';

@Component({
  selector: 'app-details',
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule,
    FormsModule,
    ArgazkiPipe,
    MatCardTitle,
  ],
  templateUrl: './details.component.html',
  styleUrl: './details.component.css',
})
export class DetailsComponent implements OnInit {
  id: number = 0;
  idCentro = 0;
  gatito: Gatito = {};
  refugio: Refugio = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private query: QueryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      this.query.getGatito(this.id).subscribe((data) => {
        this.gatito = data;
        this.idCentro = this.gatito.id_centro_acogida ?? 0;
        if (this.gatito.id_centro_acogida != null) {
          this.idCentro = this.gatito.id_centro_acogida;
          this.query.getRefugio(this.idCentro).subscribe((data) => {
            this.refugio = data;
          });
        }
      });
    });
  }

  atzera() {
    this.router.navigate(['/pages/home']);
  }

  verrefugio() {
    this.router.navigate(['/pages/refugio', this.idCentro]);
  }
}
