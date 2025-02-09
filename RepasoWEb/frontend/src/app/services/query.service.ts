import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Gatito } from '../interface/gatito';
import { Refugio } from '../interface/refugio';

@Injectable({
  providedIn: 'root',
})
export class QueryService {
  private mysqlUrl = 'http://localhost:3001';
  private jsonUrl = 'http://localhost:3000';

  constructor(private http: HttpClient) {}

  getGatitos() {
    return this.http.get<Gatito[]>(this.mysqlUrl + '/gatitos');
  }

  getGatito(id: number) {
    return this.http.get<Gatito>(`${this.mysqlUrl}/gatitos/${id}`);
  }

  updateGatito(id: number, gatito: any) {
    return this.http.put(`${this.mysqlUrl}/gatitos-update/${id}`, gatito);
  }

  deleteGatito(id: number) {
    return this.http.delete(`${this.mysqlUrl}/gatitos-delete/${id}`);
  }

  addGatito(gatito: any) {
    return this.http.post(`${this.mysqlUrl}/gatitos-gehitu`, gatito);
  }

  getRefugios() {
    return this.http.get<Refugio>(this.jsonUrl + '/refugios');
  }

  getRefugio(id: number) {
    return this.http.get<Refugio>(`${this.jsonUrl}/refugios/${id}`);
  }

  updateRefugio(id: number, refugio: any) {
    return this.http.put(`${this.jsonUrl}/refugios-update/${id}`, refugio);
  }

  deleteRefugio(id: number) {
    return this.http.delete(`${this.jsonUrl}/refugios-delete/${id}`);
  }
}
