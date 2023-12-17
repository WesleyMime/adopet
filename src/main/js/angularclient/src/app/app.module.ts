import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { TutorComponent } from './components/tutor/tutor.component';
import { TutorService } from './service/tutor/tutor.service';
import { TutorFormComponent } from './components/tutor-form/tutor-form.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    TutorComponent,
    TutorFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    TutorService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
