import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessPartnerItemComponent } from './business-partner-item.component';

describe('BusinessPartnerItemComponent', () => {
  let component: BusinessPartnerItemComponent;
  let fixture: ComponentFixture<BusinessPartnerItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BusinessPartnerItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessPartnerItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
