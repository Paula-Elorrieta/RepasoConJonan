import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { SidevarComponent } from './pages/sidevar/sidevar.component';
import { AuthGuard } from './guards/auth.guard';
import { SortuComponent } from './pages/sortu/sortu.component';
import { EditatuComponent } from './pages/editatu/editatu.component';
import { DetailsComponent } from './pages/details/details.component';
import { DetailsRefugioComponent } from './pages/details-refugio/details-refugio.component';

export const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  {
    path: 'auth',
    canActivate: [AuthGuard], // Importante
    children: [
      { path: 'login', component: LoginComponent },
      { path: '**', redirectTo: 'login' }, // Los asteriscos hacen falta
    ],
  },

  {
    path: 'pages',
    component: SidevarComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'sortu', component: SortuComponent },
      { path: 'editatu/:id', component: EditatuComponent },
      { path: 'details/:id', component: DetailsComponent },
      { path: 'refugio/:id', component: DetailsRefugioComponent },
    ],
  },
];
