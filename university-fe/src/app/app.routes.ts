import { Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {StudentslistComponent} from './studentslist/studentslist.component';
import {StudentFormComponent} from './student-form/student-form.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'students', component: StudentslistComponent},
  {path: 'student-form', component: StudentFormComponent}
];
