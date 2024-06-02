import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageloyoutComponent } from './pageloyout.component';

describe('PageloyoutComponent', () => {
  let component: PageloyoutComponent;
  let fixture: ComponentFixture<PageloyoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageloyoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageloyoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
