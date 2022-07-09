import { TestBed } from '@angular/core/testing';

import { GetReviewPeerQuestionsService } from './get-review-peer-questions.service';

describe('GetReviewPeerQuestionsService', () => {
  let service: GetReviewPeerQuestionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetReviewPeerQuestionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
