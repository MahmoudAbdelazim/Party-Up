import { TestBed } from '@angular/core/testing';

import { GetPeerRequestsAsNotificationService } from './get-peer-requests-as-notification.service';

describe('GetPeerRequestsAsNotificationService', () => {
  let service: GetPeerRequestsAsNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetPeerRequestsAsNotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
