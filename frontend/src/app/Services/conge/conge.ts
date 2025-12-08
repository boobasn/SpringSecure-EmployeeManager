import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Leave } from '../../model/leave';
import { ActivatedRoute } from '@angular/router';


@Injectable({ providedIn: 'root' })
export class CongeService {
private base = '/leaves'; // adjust if your API prefix differs
private api = 'http://localhost:8080/leaves';


constructor(private http: HttpClient ,
) {}


getAll(): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}`);
}

getMyLeaves(employeeId: string): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}/employee/${employeeId}`);
}
getByEmployee(employeeId: string): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}/employee/${employeeId}`);
}


getById(id: string): Observable<Leave> {
return this.http.get<Leave>(`${this.api}/${id}`);
}


create(leave: Leave): Observable<Leave> {
return this.http.post<Leave>(`${this.api}`, leave);
}


update(id: string, leave: Leave): Observable<Leave> {
return this.http.put<Leave>(`${this.api}/${id}`, leave);
}


delete(id: string): Observable<void> {
return this.http.delete<void>(`${this.api}/${id}`);
}


approve(id: string): Observable<Leave> {
return this.http.post<Leave>(`${this.api}/${id}/approve`, null as any);
}


reject(id: string, commentaireManager: string): Observable<Leave> {
return this.http.post<Leave>(`${this.api}/${id}/reject`, commentaireManager);
}


getApproved(): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}/approved`);
}


getRejected(): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}/rejected`);
}


getPending(): Observable<Leave[]> {
return this.http.get<Leave[]>(`${this.api}/pending`);
}
}