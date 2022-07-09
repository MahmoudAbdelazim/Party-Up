import { TestBed } from '@angular/core/testing';

import { SendReviewPeerAnswersService } from './send-review-peer-answers.service';

describe('SendReviewPeerAnswersService', () => {
  let service: SendReviewPeerAnswersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SendReviewPeerAnswersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
