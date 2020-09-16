import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultAnalysisComponent } from './result-analysis.component';

describe('ResultAnalysisComponent', () => {
  let component: ResultAnalysisComponent;
  let fixture: ComponentFixture<ResultAnalysisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResultAnalysisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultAnalysisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
