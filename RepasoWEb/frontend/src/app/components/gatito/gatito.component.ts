import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Gatito } from '../../interface/gatito';
import { ArgazkiPipe } from '../../pipes/argazki.pipe';
import { MatButtonModule } from '@angular/material/button';
import { Route, Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-gatito',
  imports: [MatCardModule, ArgazkiPipe, MatButtonModule, TranslateModule],
  standalone: true,
  templateUrl: './gatito.component.html',
  styleUrl: './gatito.component.css',
})
export class GatitoComponent {
  @Input() gatito: Gatito = {};

  constructor(
    private route: Router,
    private translateService: TranslateService
  ) {
    this.translateService.setDefaultLang('eu');
    this.translateService.use('eu');
  }

  editatu(gatito: Gatito) {
    this.route.navigate(['/pages/editatu', gatito.id]);
  }

  xehetasunaIkusi(gatito: Gatito) {
    this.route.navigate(['/pages/details', gatito.id]);
  }
}
