import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdcontactComponent } from './adcontact.component';

describe('AdcontactComponent', () => {
  let component: AdcontactComponent;
  let fixture: ComponentFixture<AdcontactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdcontactComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdcontactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
