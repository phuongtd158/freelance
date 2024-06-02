import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PolicyClientComponent } from './policy-client.component';

describe('PolicyClientComponent', () => {
  let component: PolicyClientComponent;
  let fixture: ComponentFixture<PolicyClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PolicyClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PolicyClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
