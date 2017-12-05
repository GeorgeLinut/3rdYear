import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessPartnerComponent } from './business-partner.component';

describe('BusinessPartnerComponent', () => {
  let component: BusinessPartnerComponent;
  let fixture: ComponentFixture<BusinessPartnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BusinessPartnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessPartnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
