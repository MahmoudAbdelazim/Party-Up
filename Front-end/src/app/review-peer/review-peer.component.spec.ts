import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewPeerComponent } from './review-peer.component';

describe('ReviewPeerComponent', () => {
  let component: ReviewPeerComponent;
  let fixture: ComponentFixture<ReviewPeerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReviewPeerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewPeerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
