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
  selector: 'app-editatu',
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule,
    FormsModule,
    MatCardTitle,
  ],
  templateUrl: './editatu.component.html',
  styleUrl: './editatu.component.css',
})
export class EditatuComponent implements OnInit {
  id: number = 0;
  gatito: Gatito = {};
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
      });
    });
  }

  editatu() {
    this.query.updateGatito(this.id, this.gatito).subscribe((data) => {
      console.log(data);
    });
    this.router.navigate(['/pages/home']);
  }

  ezabatu() {
    this.query.deleteGatito(this.id).subscribe((data) => {
      console.log(data);
    });
    this.router.navigate(['/pages/home']);
  }
}
