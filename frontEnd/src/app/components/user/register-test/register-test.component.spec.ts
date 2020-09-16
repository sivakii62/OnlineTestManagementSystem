import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterTestComponent } from './register-test.component';

describe('RegisterTestComponent', () => {
  let component: RegisterTestComponent;
  let fixture: ComponentFixture<RegisterTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
