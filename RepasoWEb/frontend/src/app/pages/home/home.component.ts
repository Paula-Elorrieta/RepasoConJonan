import { Component, OnInit } from '@angular/core';
import { QueryService } from '../../services/query.service';
import { Gatito } from '../../interface/gatito';
import { CommonModule } from '@angular/common';
import { GatitoComponent } from '../../components/gatito/gatito.component';
import { SidevarComponent } from "../sidevar/sidevar.component";
import { SwitchHizkuntzaComponent } from "../../components/switch-hizkuntza/switch-hizkuntza.component";


@Component({
  selector: 'app-home',
  imports: [CommonModule, GatitoComponent, SwitchHizkuntzaComponent],
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  gatitos: Gatito[] = [];

  constructor(private query: QueryService) {}

  ngOnInit() {
    this.query.getGatitos().subscribe((data) => {
      this.gatitos = data;
    });
  }
}
