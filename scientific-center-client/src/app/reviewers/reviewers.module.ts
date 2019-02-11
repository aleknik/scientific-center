import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewerListComponent } from './reviewer-list/reviewer-list.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ReviewerListComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class ReviewersModule { }
