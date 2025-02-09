import { Component } from '@angular/core';
import { SelectModule } from 'primeng/select';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-switch-hizkuntza',
  imports: [
    SelectModule,
    TranslateModule,
    MatFormField,
    MatLabel,
    MatOptionModule,
    MatSelectModule,
    CommonModule
  ],
  standalone: true,
  templateUrl: './switch-hizkuntza.component.html',
  styleUrls: ['./switch-hizkuntza.component.css'],
})
export class SwitchHizkuntzaComponent {
  selected = 'eu';



  constructor(private translateService: TranslateService) {
    this.translateService.setDefaultLang(this.selected);
    this.translateService.use(this.selected);
  }

  hizkuntzaAldatu(nuevaHizkuntza: string): void {
    this.selected = nuevaHizkuntza;
    this.translateService.use(this.selected);
  }
}
