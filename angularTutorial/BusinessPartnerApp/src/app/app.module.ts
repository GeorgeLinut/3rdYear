import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BusinessPartnerComponent } from './business-partner/business-partner.component';
import { BusinessPartnerItemComponent } from './business-partner/business-partner-item/business-partner-item.component';

@NgModule({
  declarations: [
    AppComponent,
    BusinessPartnerComponent,
    BusinessPartnerItemComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
