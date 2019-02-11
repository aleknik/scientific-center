import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { RoutesModule } from './routes/routes.module';
import { PapersModule } from './papers/papers.module';
import { PaymentsModule } from './payments/payments.module';
import { JournalsModule } from './journals/journals.module';
import { ReviewersModule } from './reviewers/reviewers.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    RoutesModule,
    CoreModule,
    AuthModule,
    PapersModule,
    PaymentsModule,
    JournalsModule,
    ReviewersModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
