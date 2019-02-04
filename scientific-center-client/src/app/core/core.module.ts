import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { SharedModule } from '../shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RoutesModule } from '../routes/routes.module';
import { TokenUtilsService } from './util/token-utils.service';
import { TokenInterceptorService } from './http/token-interceptor.service';

@NgModule({
  declarations: [NavComponent],
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule
  ],
  exports: [NavComponent],
  providers: [
    TokenUtilsService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ]
})
export class CoreModule { }
