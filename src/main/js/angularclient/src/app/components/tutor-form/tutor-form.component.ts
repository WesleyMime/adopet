import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Tutor } from 'src/app/model/tutor';
import { TutorService } from 'src/app/service/tutor/tutor.service';

@Component({
  selector: 'app-tutor-form',
  templateUrl: './tutor-form.component.html',
  styleUrls: ['./tutor-form.component.css']
})
export class TutorFormComponent {

  tutor: Tutor;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tutorService: TutorService) {
      this.tutor = new Tutor('', '', '', '');
   }

   onSubmit() {
    this.tutorService.save(this.tutor).subscribe(result => this.gotoTutoresList());
   }
   
   gotoTutoresList() {
    this.router.navigate(['/tutores']);
   }
}
