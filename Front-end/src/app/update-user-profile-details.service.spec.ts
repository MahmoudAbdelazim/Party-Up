import { TestBed } from '@angular/core/testing';

import { UpdateUserProfileDetailsService } from './update-user-profile-details.service';

describe('UpdateUserProfileDetailsService', () => {
  let service: UpdateUserProfileDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateUserProfileDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
