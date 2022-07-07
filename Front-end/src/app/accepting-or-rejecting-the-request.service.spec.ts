import { TestBed } from '@angular/core/testing';

import { AcceptingOrRejectingTheRequestService } from './accepting-or-rejecting-the-request.service';

describe('AcceptingOrRejectingTheRequestService', () => {
  let service: AcceptingOrRejectingTheRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcceptingOrRejectingTheRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
