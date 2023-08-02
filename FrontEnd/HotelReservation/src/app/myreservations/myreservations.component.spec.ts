import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyreservationsComponent } from './myreservations.component';

describe('MyreservationsComponent', () => {
  let component: MyreservationsComponent;
  let fixture: ComponentFixture<MyreservationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyreservationsComponent]
    });
    fixture = TestBed.createComponent(MyreservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
