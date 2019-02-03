import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaperListComponent } from './paper-list/paper-list.component';
import { NewPaperComponent } from './new-paper/new-paper.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [PaperListComponent, NewPaperComponent],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class PapersModule { }
