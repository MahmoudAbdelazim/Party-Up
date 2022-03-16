import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonalityTestComponent } from './personality-test.component';

describe('PersonalityTestComponent', () => {
  let component: PersonalityTestComponent;
  let fixture: ComponentFixture<PersonalityTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonalityTestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonalityTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
