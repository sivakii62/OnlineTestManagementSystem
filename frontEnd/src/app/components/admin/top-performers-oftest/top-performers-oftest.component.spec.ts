import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopPerformersOftestComponent } from './top-performers-oftest.component';

describe('TopPerformersOftestComponent', () => {
  let component: TopPerformersOftestComponent;
  let fixture: ComponentFixture<TopPerformersOftestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopPerformersOftestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopPerformersOftestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
