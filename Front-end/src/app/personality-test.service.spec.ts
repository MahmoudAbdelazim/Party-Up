import { TestBed } from '@angular/core/testing';

import { PersonalityTestService } from './personality-test.service';

describe('PersonalityTestService', () => {
  let service: PersonalityTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalityTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
