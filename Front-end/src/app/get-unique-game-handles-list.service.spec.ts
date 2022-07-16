import { TestBed } from '@angular/core/testing';

import { GetUniqueGameHandlesListService } from './get-unique-game-handles-list.service';

describe('GetUniqueGameHandlesListService', () => {
  let service: GetUniqueGameHandlesListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetUniqueGameHandlesListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
