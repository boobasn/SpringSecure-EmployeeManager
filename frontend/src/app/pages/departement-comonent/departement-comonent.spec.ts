import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartementComonent } from './departement-comonent';

describe('DepartementComonent', () => {
  let component: DepartementComonent;
  let fixture: ComponentFixture<DepartementComonent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartementComonent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepartementComonent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
