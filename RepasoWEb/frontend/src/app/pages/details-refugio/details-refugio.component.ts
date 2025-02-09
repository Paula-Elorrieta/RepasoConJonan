import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ArgazkiPipe } from '../../pipes/argazki.pipe';
import * as mapboxgl from 'mapbox-gl';
import { Refugio } from '../../interface/refugio';
import { ActivatedRoute, Router } from '@angular/router';
import { QueryService } from '../../services/query.service';

@Component({
  selector: 'app-details-refugio',
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    CommonModule,
    FormsModule,
    MatCardTitle,
  ],
  templateUrl: './details-refugio.component.html',
  styleUrl: './details-refugio.component.css',
})
export class DetailsRefugioComponent implements OnInit {
  refugio: Refugio = {};
  id: number = 0;
  latUmt = 0;
  lngUmt = 0;
  token =
    'pk.eyJ1Ijoiam9uYW5kZXI3NzciLCJhIjoiY201anl5OHl0MWM5dDJoc2RlYzQwendweCJ9.a7mwB8YdWCJK_zd17-E9SQ';
  map!: mapboxgl.Map;

  constructor(
    private activatedRoute: ActivatedRoute,
    private query: QueryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.id = params['id'];
      this.query.getRefugio(this.id).subscribe((data) => {
        this.refugio = data;
        this.initMap();
      });
    });
  }

  atzera() {
    this.router.navigate(['/pages/home']);
  }

  initMap() {
    if (this.refugio && this.refugio.latitud && this.refugio.longitud) {
      this.map = new mapboxgl.Map({
        accessToken: this.token,
        container: 'mapa',
        style: 'mapbox://styles/mapbox/streets-v11',
        center: [this.refugio.latitud, this.refugio.longitud],
        zoom: 16,
      });

      new mapboxgl.Marker({ color: 'blue' })
        .setLngLat([this.refugio.latitud, this.refugio.longitud])
        .setPopup(
          new mapboxgl.Popup().setHTML(`
        <div style="color: black;">

          <h3 style="color:rgb(112, 0, 204);">${
            this.refugio.nombre || 'Nombre no disponible'
          }</h3>
        </div>

        `)
        )
        .addTo(this.map);
    } else {
      console.error('Ez dira topatu koordenatuak.');
    }
  }
}
