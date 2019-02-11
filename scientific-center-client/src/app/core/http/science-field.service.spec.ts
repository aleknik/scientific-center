import { TestBed } from '@angular/core/testing';

import { ScienceFieldService } from './science-field.service';

describe('ScienceFieldService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScienceFieldService = TestBed.get(ScienceFieldService);
    expect(service).toBeTruthy();
  });
});
