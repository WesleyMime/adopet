import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TutorComponent } from './components/tutor/tutor.component';
import { TutorFormComponent } from './components/tutor-form/tutor-form.component';

const routes: Routes = [
  { path: 'tutores', component: TutorComponent },
  { path: 'add-tutor', component: TutorFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
