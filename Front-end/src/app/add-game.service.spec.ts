import { TestBed } from '@angular/core/testing';

import { AddGameService } from './add-game.service';

describe('AddGameService', () => {
  let service: AddGameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddGameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
