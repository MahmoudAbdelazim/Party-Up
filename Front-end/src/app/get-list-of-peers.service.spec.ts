import { TestBed } from '@angular/core/testing';

import { GetListOfPeersService } from './get-list-of-peers.service';

describe('GetListOfPeersService', () => {
  let service: GetListOfPeersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetListOfPeersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
