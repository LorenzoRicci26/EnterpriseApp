import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

export interface Grade{
  subject: string;
  value: number;
  examDate: Date;
}

export interface Student {
  id: number;
  name: string;
  surname: string;
  email: string;
  age: number;
  dob: Date;
  gender: string;
  faculty: string;
  enrollmentYear: number;
  grades: Grade[];
  active: boolean;
}
@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8080/api/students';
  constructor(private http: HttpClient) { }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl);
  }

  createStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(this.apiUrl+"/add", student);
  }
}
