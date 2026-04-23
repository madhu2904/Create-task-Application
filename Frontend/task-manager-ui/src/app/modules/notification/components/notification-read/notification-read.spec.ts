import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationRead } from './notification-read';

describe('NotificationRead', () => {
  let component: NotificationRead;
  let fixture: ComponentFixture<NotificationRead>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotificationRead]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationRead);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
