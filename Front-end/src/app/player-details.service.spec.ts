import { TestBed } from '@angular/core/testing';

import { PlayerDetailsService } from './player-details.service';

describe('PlayerDetailsService', () => {
  let service: PlayerDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayerDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
