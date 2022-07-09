import { TestBed } from '@angular/core/testing';

import { IfLoggedInGuard } from './if-logged-in.guard';

describe('IfLoggedInGuard', () => {
  let guard: IfLoggedInGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IfLoggedInGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
