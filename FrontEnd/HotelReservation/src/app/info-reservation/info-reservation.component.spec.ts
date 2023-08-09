import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoReservationComponent } from './info-reservation.component';

describe('InfoReservationComponent', () => {
  let component: InfoReservationComponent;
  let fixture: ComponentFixture<InfoReservationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfoReservationComponent]
    });
    fixture = TestBed.createComponent(InfoReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
