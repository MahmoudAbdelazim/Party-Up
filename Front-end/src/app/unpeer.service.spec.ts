import { TestBed } from '@angular/core/testing';

import { UnpeerService } from './unpeer.service';

describe('UnpeerService', () => {
  let service: UnpeerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnpeerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
