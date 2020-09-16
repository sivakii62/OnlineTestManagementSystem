import { TestBed } from '@angular/core/testing';

import { AttemptExamService } from './attempt-exam.service';

describe('AttemptExamService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AttemptExamService = TestBed.get(AttemptExamService);
    expect(service).toBeTruthy();
  });
});
