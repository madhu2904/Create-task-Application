import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationUnread } from './notification-unread';

describe('NotificationUnread', () => {
  let component: NotificationUnread;
  let fixture: ComponentFixture<NotificationUnread>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotificationUnread]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationUnread);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
