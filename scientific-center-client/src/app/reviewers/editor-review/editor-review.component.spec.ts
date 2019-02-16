import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorReviewComponent } from './editor-review.component';

describe('EditorReviewComponent', () => {
  let component: EditorReviewComponent;
  let fixture: ComponentFixture<EditorReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
