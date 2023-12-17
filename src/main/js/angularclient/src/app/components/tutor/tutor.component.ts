import { Component, OnInit } from '@angular/core';
import { Tutor } from 'src/app/model/tutor';
import { TutorService } from 'src/app/service/tutor/tutor.service';

@Component({
  selector: 'app-tutor',
  templateUrl: './tutor.component.html',
  styleUrls: ['./tutor.component.css']
})
export class TutorComponent implements OnInit {

  tutores: Tutor[] = [];

  constructor(private tutorService: TutorService) { }

  ngOnInit(): void {
    this.tutorService.findAll().subscribe(data => {
      this.tutores = data;
    });
  }

}
