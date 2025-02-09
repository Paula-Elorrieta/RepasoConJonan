import { Component } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-sidevar',
  imports: [ MatSidenavModule, MatToolbarModule, MatButtonModule, MatIconModule, MatListModule, RouterOutlet, RouterLink ],
  templateUrl: './sidevar.component.html',
  styleUrl: './sidevar.component.css'
})
export class SidevarComponent {

  constructor() { }

  ngOnInit() {
  }

}
