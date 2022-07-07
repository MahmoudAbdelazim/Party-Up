import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPeersComponent } from './my-peers.component';

describe('MyPeersComponent', () => {
  let component: MyPeersComponent;
  let fixture: ComponentFixture<MyPeersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyPeersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPeersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
