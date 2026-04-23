import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationCreate } from './notification-create';

describe('NotificationCreate', () => {
  let component: NotificationCreate;
  let fixture: ComponentFixture<NotificationCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotificationCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
