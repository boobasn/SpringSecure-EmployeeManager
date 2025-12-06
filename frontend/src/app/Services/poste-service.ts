import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Poste } from '../model/poste';
import { Observable, tap } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class PosteService {

  private api = 'http://localhost:8080/postes';

  constructor(private http: HttpClient) {}

  getPostes(): Observable<Poste[]> {
    return this.http.get<Poste[]>(this.api);
  }
}
