import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyhotelsComponent } from './myhotels.component';

describe('MyhotelsComponent', () => {
  let component: MyhotelsComponent;
  let fixture: ComponentFixture<MyhotelsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MyhotelsComponent]
    });
    fixture = TestBed.createComponent(MyhotelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
