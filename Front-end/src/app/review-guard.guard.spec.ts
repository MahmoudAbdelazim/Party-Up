import { TestBed } from '@angular/core/testing';

import { ReviewGuardGuard } from './review-guard.guard';

describe('ReviewGuardGuard', () => {
  let guard: ReviewGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ReviewGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
