import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindpeersComponent } from './findpeers.component';

describe('FindpeersComponent', () => {
  let component: FindpeersComponent;
  let fixture: ComponentFixture<FindpeersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindpeersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FindpeersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
