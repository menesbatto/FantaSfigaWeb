import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GazzettaCredentialsComponent } from './gazzetta-credentials.component';

describe('GazzettaCredentialsComponent', () => {
  let component: GazzettaCredentialsComponent;
  let fixture: ComponentFixture<GazzettaCredentialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GazzettaCredentialsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GazzettaCredentialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
