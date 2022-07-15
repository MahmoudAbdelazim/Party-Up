import { TestBed } from '@angular/core/testing';

import { GetListOfGamesService } from './get-list-of-games.service';

describe('GetListOfGamesService', () => {
  let service: GetListOfGamesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetListOfGamesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
