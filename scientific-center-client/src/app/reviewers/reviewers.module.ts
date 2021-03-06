import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewerListComponent } from './reviewer-list/reviewer-list.component';
import { SharedModule } from '../shared/shared.module';
import { ReviewPaperComponent } from './review-paper/review-paper.component';
import { EditorReviewComponent } from './editor-review/editor-review.component';
import { EditorRevisionReviewComponent } from './editor-revision-review/editor-revision-review.component';
import { ChooseNewReviewerComponent } from './choose-new-reviewer/choose-new-reviewer.component';

@NgModule({
  declarations: [ReviewerListComponent, ReviewPaperComponent, EditorReviewComponent, EditorRevisionReviewComponent, ChooseNewReviewerComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class ReviewersModule { }
