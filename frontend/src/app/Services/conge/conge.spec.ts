import { TestBed } from '@angular/core/testing';

import { CongeService } from './conge';

describe('Conge', () => {
  let service: CongeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CongeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
