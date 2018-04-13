import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionRulesComponent } from './competition-rules.component';

describe('CompetitionRulesComponent', () => {
  let component: CompetitionRulesComponent;
  let fixture: ComponentFixture<CompetitionRulesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompetitionRulesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
