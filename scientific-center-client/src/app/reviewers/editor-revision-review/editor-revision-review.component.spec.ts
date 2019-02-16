import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorRevisionReviewComponent } from './editor-revision-review.component';

describe('EditorRevisionReviewComponent', () => {
  let component: EditorRevisionReviewComponent;
  let fixture: ComponentFixture<EditorRevisionReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorRevisionReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorRevisionReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
