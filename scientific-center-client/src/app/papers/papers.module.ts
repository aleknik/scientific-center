import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaperListComponent } from './paper-list/paper-list.component';
import { NewPaperComponent } from './new-paper/new-paper.component';
import { SharedModule } from '../shared/shared.module';
import { PaperDetailsComponent } from './paper-details/paper-details.component';
import { PaperRelevantComponent } from './paper-relevant/paper-relevant.component';

@NgModule({
  declarations: [PaperListComponent, NewPaperComponent, PaperDetailsComponent, PaperRelevantComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class PapersModule { }
