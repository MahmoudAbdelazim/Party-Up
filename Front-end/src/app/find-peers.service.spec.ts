import { TestBed } from '@angular/core/testing';

import { FindPeersService } from './find-peers.service';

describe('FindPeersService', () => {
  let service: FindPeersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FindPeersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
