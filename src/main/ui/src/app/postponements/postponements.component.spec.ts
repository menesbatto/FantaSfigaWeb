import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostponementsComponent } from './postponements.component';

describe('PostponementsComponent', () => {
  let component: PostponementsComponent;
  let fixture: ComponentFixture<PostponementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostponementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostponementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
