import { TestBed } from '@angular/core/testing';

import { GetUploadedImageService } from './get-uploaded-image.service';

describe('GetUploadedImageService', () => {
  let service: GetUploadedImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetUploadedImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
