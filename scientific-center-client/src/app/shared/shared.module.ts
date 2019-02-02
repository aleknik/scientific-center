import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { FormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  imports: [
    CommonModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    FormsModule,
    ModalModule.forRoot(),
    TypeaheadModule.forRoot(),
    AngularFontAwesomeModule,
    TabsModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      preventDuplicates: true,
      positionClass: 'toast-bottom-right'
    })
  ],
  declarations: [],
  exports: [
    BsDropdownModule,
    CollapseModule,
    FormsModule,
    TypeaheadModule,
    TabsModule
  ],
})
export class SharedModule { }
