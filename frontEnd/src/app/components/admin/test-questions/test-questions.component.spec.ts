import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestQuestionsComponent } from './test-questions.component';

describe('TestQuestionsComponent', () => {
  let component: TestQuestionsComponent;
  let fixture: ComponentFixture<TestQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
