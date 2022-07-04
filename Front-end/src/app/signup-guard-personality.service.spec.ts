import { TestBed } from '@angular/core/testing';

import { SignupGuardPersonalityService } from './signup-guard-personality.service';

describe('SignupGuardPersonalityService', () => {
  let service: SignupGuardPersonalityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SignupGuardPersonalityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
