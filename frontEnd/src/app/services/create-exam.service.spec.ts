import { TestBed } from '@angular/core/testing';

import { CreateExamService } from './create-exam.service';

describe('CreateExamService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreateExamService = TestBed.get(CreateExamService);
    expect(service).toBeTruthy();
  });
});
