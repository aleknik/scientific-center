import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperRelevantComponent } from './paper-relevant.component';

describe('PaperRelevantComponent', () => {
  let component: PaperRelevantComponent;
  let fixture: ComponentFixture<PaperRelevantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaperRelevantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperRelevantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
