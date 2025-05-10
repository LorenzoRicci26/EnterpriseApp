import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';
import {Student} from '../services/student.service';
import {StudentService} from '../services/student.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-studentslist',
  imports: [
    CommonModule,
    NavbarComponent,
  ],
  templateUrl: './studentslist.component.html',
  styleUrl: './studentslist.component.scss'
})
export class StudentslistComponent implements OnInit{
  title = 'university-fe';
  students: Student[] = [];

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.studentService.getStudents().subscribe(
      (students: Student[]) => this.students = students);
  }
}
