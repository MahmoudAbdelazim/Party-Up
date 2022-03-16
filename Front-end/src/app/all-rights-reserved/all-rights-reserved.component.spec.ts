import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRightsReservedComponent } from './all-rights-reserved.component';

describe('AllRightsReservedComponent', () => {
  let component: AllRightsReservedComponent;
  let fixture: ComponentFixture<AllRightsReservedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRightsReservedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllRightsReservedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
