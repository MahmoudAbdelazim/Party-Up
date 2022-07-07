import { TestBed } from '@angular/core/testing';

import { SendPeerRequestService } from './send-peer-request.service';

describe('SendPeerRequestService', () => {
  let service: SendPeerRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SendPeerRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
