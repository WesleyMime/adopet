import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TutorFormComponent } from './tutor-form.component';

describe('TutorFormComponent', () => {
  let component: TutorFormComponent;
  let fixture: ComponentFixture<TutorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TutorFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TutorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
