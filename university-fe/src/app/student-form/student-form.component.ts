import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Student, StudentService} from '../services/student.service';
import { Router } from '@angular/router';
import {NavbarComponent} from '../navbar/navbar.component';

@Component({
  selector: 'app-student-form',
  imports: [
    ReactiveFormsModule,
    NavbarComponent
  ],
  templateUrl: './student-form.component.html',
  styleUrl: './student-form.component.scss'
})
export class StudentFormComponent implements OnInit{
  studentForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private router: Router
  ) {
    // Costruzione del form con tutti i campi dello studente
    this.studentForm = this.fb.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      age: ['', [Validators.required, Validators.min(18), Validators.max(100)]],
      email: ['', [Validators.required, Validators.email]],
      dob: ['', [Validators.required]],
      faculty: ['', [Validators.required]],
      enrollmentYear: ['', [Validators.required]],
      active: [true],
      grades: this.fb.array([])
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.studentForm.valid) {
      const studentDTO: Student = {
        ...this.studentForm.value
      };
      this.studentService.createStudent(studentDTO).subscribe(
        (response) => {
          console.log('Studente creato con successo!', response);
        },
        (error) => {
          console.error('Errore nella creazione dello studente:', error);
        }
      );
    }
  }
}
