import { TestBed } from '@angular/core/testing';

import { GetOthersProfileService } from './get-others-profile.service';

describe('GetOthersProfileService', () => {
  let service: GetOthersProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetOthersProfileService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
