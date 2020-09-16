import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CaluculateScoreComponent } from './caluculate-score.component';

describe('CaluculateScoreComponent', () => {
  let component: CaluculateScoreComponent;
  let fixture: ComponentFixture<CaluculateScoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaluculateScoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaluculateScoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
