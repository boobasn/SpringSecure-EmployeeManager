import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Department } from '../model/departement';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DepartementService {

  private api = 'http://localhost:8080/departments';

  constructor(private http: HttpClient) {}

  getDepartements(): Observable<Department[]> {
    return this.http.get<Department[]>(this.api);
  }
}