import { TestBed } from '@angular/core/testing';

import { GuardAuthenticationService } from './guard-authentication.service';

describe('GuardAuthenticationService', () => {
  let service: GuardAuthenticationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GuardAuthenticationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
