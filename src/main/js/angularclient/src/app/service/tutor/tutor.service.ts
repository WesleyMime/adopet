import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tutor } from 'src/app/model/tutor';

@Injectable({
  providedIn: 'root'
})
export class TutorService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080';
  }

  public findAll(): Observable<Tutor[]> {
    return this.http.get<Tutor[]>(`${this.usersUrl}/tutores`);
  }

  public save(owner: Tutor) {
    return this.http.post<Tutor>(`${this.usersUrl}/tutores`, owner);
  }
}
