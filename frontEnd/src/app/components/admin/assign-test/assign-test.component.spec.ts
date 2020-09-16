import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignTestComponent } from './assign-test.component';

describe('AssignTestComponent', () => {
  let component: AssignTestComponent;
  let fixture: ComponentFixture<AssignTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
